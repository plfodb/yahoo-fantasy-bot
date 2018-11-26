package com.pldfodb.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pldfodb.controller.model.yahoo.TransactionSourceType;
import com.pldfodb.model.Player;
import com.pldfodb.model.PlayerTransaction;
import com.pldfodb.model.Transaction;
import com.pldfodb.repo.rowmapper.TransactionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry;

@Repository
public class TransactionRepository {

    @Autowired private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper mapper;

    @Transactional
    public void addTransactions(Set<Transaction> transactions) {

        if (transactions.isEmpty())
            return;

        List<SqlParameterSource> transactionParams = new ArrayList<>(transactions.size());
        List<SqlParameterSource> playerParams = new ArrayList<>(transactions.size() * 2);
        List<SqlParameterSource> playerTransactionParams = new ArrayList<>(transactions.size() * 2);
        for (Transaction t : transactions) {
            transactionParams.add(new MapSqlParameterSource()
                    .addValue("transactionId", t.getId())
                    .addValue("type", t.getType().name())
                    .addValue("dateExecuted", new java.sql.Date(t.getDateExecuted().toEpochMilli()))
                    .addValue("sourceTeam", t.getSourceTeam())
                    .addValue("destinationTeam", t.getDestinationTeam()));

            Integer transactionId = t.getId();
            Stream.concat(t.getSourcePlayers().entrySet().stream(), t.getDestinationPlayers().entrySet().stream()).forEach(entry -> {

                Player player = entry.getKey();
                PlayerTransaction playerTransaction = entry.getValue();
                playerParams.add(new MapSqlParameterSource()
                        .addValue("id", player.getId())
                        .addValue("name", player.getName())
                        .addValue("position", player.getPosition()));

                playerTransactionParams.add(new MapSqlParameterSource()
                        .addValue("transactionId", transactionId)
                        .addValue("playerId", player.getId())
                        .addValue("source", playerTransaction.getSource().name())
                        .addValue("destination", playerTransaction.getDestination().name()));
            });
        }
        jdbcTemplate.batchUpdate("INSERT INTO transactions VALUES (:transactionId, :type, :dateExecuted, :sourceTeam, :destinationTeam)", transactionParams.toArray(new SqlParameterSource[transactionParams.size()]));
        jdbcTemplate.batchUpdate(batchPlayerUpdateQuery, playerParams.toArray(new SqlParameterSource[playerParams.size()]));
        jdbcTemplate.batchUpdate("INSERT INTO player_transactions VALUES (:transactionId, :playerId, :source, :destination)", playerTransactionParams.toArray(new SqlParameterSource[playerTransactionParams.size()]));
    }

    public List<Transaction> getLatestTransactions(int count) throws IOException {

        List<Transaction> transactions = jdbcTemplate.query("SELECT * FROM transactions ORDER BY transaction_id DESC LIMIT :count", new MapSqlParameterSource().addValue("count", count), new TransactionRowMapper());

        for (Transaction transaction : transactions) {
            Map<Player, PlayerTransaction> playerTransactions = jdbcTemplate.query("SELECT * FROM player_transactions JOIN players USING (player_id) WHERE transaction_id = :transactionId", new MapSqlParameterSource().addValue("transactionId", transaction.getId()), new ResultSetExtractor<Map<Player, PlayerTransaction>>() {

                @Override
                public Map<Player, PlayerTransaction> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    Map<Player, PlayerTransaction> playerTransactions = new HashMap<>();
                    while (rs.next()) {
                        Player player = new Player(rs.getLong("player_id"), rs.getString("name"), rs.getString("pos"));
                        playerTransactions.put(player, new PlayerTransaction(TransactionSourceType.valueOf(rs.getString("source")), TransactionSourceType.valueOf(rs.getString("destination"))));
                    }
                    return playerTransactions;
                }
            });

            Map<Player, PlayerTransaction> sourcePlayers = playerTransactions.entrySet().stream().filter(e -> e.getValue().getDestination().equals(TransactionSourceType.TEAM)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            Map<Player, PlayerTransaction> destPlayers = playerTransactions.entrySet().stream().filter(e -> e.getValue().getSource().equals(TransactionSourceType.TEAM)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            transaction.setSourcePlayers(sourcePlayers);
            transaction.setDestinationPlayers(destPlayers);
        }

        return transactions;
    }

    private static final String batchPlayerUpdateQuery = "INSERT INTO players (player_id, name, pos) VALUES (:id, :name, :position) ON CONFLICT (player_id) DO UPDATE SET name = :name, pos = :position";
}

package com.pldfodb.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pldfodb.model.*;
import com.pldfodb.repo.rowmapper.MatchupRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class MatchupRepository {

    @Autowired private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper mapper;

    public void saveMatchups(Collection<Matchup> matchups) {

        List<SqlParameterSource> params = new ArrayList<>();
        matchups.forEach(matchup -> {

            MatchupTeam first = matchup.getFirst();
            MatchupTeam second = matchup.getSecond();
            params.add(new MapSqlParameterSource()
                    .addValue("week", matchup.getWeek())
                    .addValue("playoffs", matchup.isPlayoffs())
                    .addValue("firstTeamId", first.getTeamId())
                    .addValue("firstTeamScored", first.getScored())
                    .addValue("firstTeamProjected", first.getProjected())
                    .addValue("firstTeamProbability", first.getWinProbability())
                    .addValue("secondTeamId", second.getTeamId())
                    .addValue("secondTeamScored", second.getScored())
                    .addValue("secondTeamProjected", second.getProjected())
                    .addValue("secondTeamProbability", second.getWinProbability()));
        });

        jdbcTemplate.batchUpdate(MATCHUP_UPSERT_QUERY, params.toArray(new SqlParameterSource[params.size()]));
    }

    public List<Matchup> getLatestMatchups() {

        return jdbcTemplate.query(LATEST_MATCHUPS_QUERY, new MapSqlParameterSource(), new MatchupRowMapper());
    }

    public List<Matchup> getMatchups(int week) {

        return jdbcTemplate.query("SELECT * FROM matchups WHERE week = :week", new MapSqlParameterSource().addValue("week", week), new MatchupRowMapper());
    }

    private static final String MATCHUP_UPSERT_QUERY = "INSERT INTO matchups VALUES (:week, :playoffs, :firstTeamId, :firstTeamScored, :firstTeamProjected, :firstTeamProbability, :secondTeamId, :secondTeamScored, :secondTeamProjected, :secondTeamProbability)" +
            "ON CONFLICT (week, first_team_id, second_team_id)" +
            " DO UPDATE SET playoffs = :playoffs, first_team_scored = :firstTeamScored, first_team_projected = :firstTeamProjected, first_team_probability = :firstTeamProbability, second_team_scored = :secondTeamScored, second_team_projected = :secondTeamProjected, second_team_probability = :secondTeamProbability";

    private static final String LATEST_MATCHUPS_QUERY =
            "WITH latest_week AS (" +
            "    SELECT week" +
            "    FROM matchups" +
            "    GROUP BY week" +
            "    ORDER BY week DESC" +
            "    LIMIT 1" +
            ")" +
            "SELECT *" +
            "FROM matchups" +
            "    JOIN latest_week USING (week)";
}

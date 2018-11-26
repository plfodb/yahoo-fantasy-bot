package com.pldfodb.repo.rowmapper;

import com.pldfodb.controller.model.yahoo.TransactionType;
import com.pldfodb.model.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Transaction(rs.getInt("transaction_id"),
                TransactionType.valueOf(rs.getString("type")),
                rs.getTimestamp("date_executed").toInstant(),
                rs.getString("source_team"),
                rs.getString("dest_team"));
    }
}

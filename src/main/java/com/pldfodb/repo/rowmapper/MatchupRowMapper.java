package com.pldfodb.repo.rowmapper;

import com.pldfodb.model.Matchup;
import com.pldfodb.model.MatchupTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchupRowMapper implements RowMapper<Matchup> {

    @Override
    public Matchup mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchupTeam first = new MatchupTeam(
                rs.getInt("first_team_id"),
                rs.getDouble("first_team_scored"),
                rs.getDouble("first_team_projected"),
                rs.getDouble("first_team_probability"));

        MatchupTeam second = new MatchupTeam(
                rs.getInt("second_team_id"),
                rs.getDouble("second_team_scored"),
                rs.getDouble("second_team_projected"),
                rs.getDouble("second_team_probability"));

        return new Matchup(rs.getInt("week"), rs.getBoolean("playoffs"), first, second);
    }
}

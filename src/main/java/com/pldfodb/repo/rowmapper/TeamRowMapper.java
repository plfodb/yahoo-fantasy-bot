package com.pldfodb.repo.rowmapper;

import com.pldfodb.model.Manager;
import com.pldfodb.model.Matchup;
import com.pldfodb.model.MatchupTeam;
import com.pldfodb.model.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class TeamRowMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {

        Manager manager = new Manager(rs.getInt("manager_id"), rs.getString("manager_nickname"));
        return new Team(rs.getInt("team_id"), rs.getString("name"), rs.getBoolean("clinched_playoffs"), Arrays.asList(manager));
    }
}

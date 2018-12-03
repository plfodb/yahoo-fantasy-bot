package com.pldfodb.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pldfodb.model.Manager;
import com.pldfodb.model.Team;
import com.pldfodb.repo.rowmapper.TeamRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class TeamRepository {

    @Autowired private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper mapper;

    public void saveTeams(Collection<Team> teams) {

        List<SqlParameterSource> params = new ArrayList<>();
        teams.forEach(team -> {

            Manager manager = team.getManagers().iterator().next();
            params.add(new MapSqlParameterSource()
                    .addValue("id", team.getId())
                    .addValue("name", team.getName())
                    .addValue("clinchedPlayoffs", team.isClinchedPlayoffs())
                    .addValue("managerId", manager.getId())
                    .addValue("managerNickname", manager.getNickname()));
        });

        jdbcTemplate.batchUpdate(TEAM_UPSERT_QUERY, params.toArray(new SqlParameterSource[params.size()]));
    }

    public Team getTeam(Integer id) {

       List<Team> teams = getTeams(Arrays.asList(id));
        if (teams.isEmpty())
            return null;

        return teams.iterator().next();
    }

    public List<Team> getTeams(List<Integer> teamIds) {

        return jdbcTemplate.query("SELECT * FROM teams WHERE team_id = ANY(:teamIds)", new MapSqlParameterSource().addValue("teamIds", teamIds), new TeamRowMapper());
    }

    private static final String TEAM_UPSERT_QUERY = "INSERT INTO teams VALUES (:id, :name, :clinchedPlayoffs, :managerId, :managerNickname) ON CONFLICT (team_id) DO UPDATE SET name = :name, clinched_playoffs = :clinchedPlayoffs, manager_id = :managerId, manager_nickname = :managerNickname";
}

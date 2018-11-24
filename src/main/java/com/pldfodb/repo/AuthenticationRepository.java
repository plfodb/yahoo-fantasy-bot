package com.pldfodb.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Repository
public class AuthenticationRepository {

    @Autowired private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper mapper;

    @Transactional
    public void updateToken(OAuth2AccessToken token) throws JsonProcessingException {

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("token", mapper.writeValueAsString(token));
        jdbcTemplate.update("TRUNCATE tokens", new MapSqlParameterSource());
        jdbcTemplate.update("INSERT INTO tokens VALUES (:token)", namedParameters);
    }

    public OAuth2AccessToken getToken() throws IOException {

        String tokenJson = jdbcTemplate.queryForObject("SELECT * FROM tokens LIMIT 1", new MapSqlParameterSource(), String.class);
        return mapper.readValue(tokenJson, OAuth2AccessToken.class);
    }

    private String fileListQuery = "SELECT * FROM files ORDER BY upload_time DESC LIMIT :limit OFFSET :offset";
    private String insertFileQuery = "INSERT INTO files VALUES (:id, :name, :uploadTime, :size)";
    private String getFileQuery = "SELECT * FROM files WHERE id = :id";
}

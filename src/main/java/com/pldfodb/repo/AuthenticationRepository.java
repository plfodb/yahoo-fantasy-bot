package com.pldfodb.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pldfodb.oauth.StaticExpirationOAuth2AccessToken;
import com.pldfodb.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class AuthenticationRepository {

    @Autowired private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper mapper;

    private static final Logger LOGGER = Logger.getLogger(AuthenticationRepository.class.getName());

    @Transactional
    public void updateToken(OAuth2AccessToken token) throws JsonProcessingException {

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("token", mapper.writeValueAsString(token));
        jdbcTemplate.update("TRUNCATE tokens", new MapSqlParameterSource());
        jdbcTemplate.update("INSERT INTO tokens VALUES (:token)", namedParameters);
    }

    public OAuth2AccessToken getToken() {

        List<String> results = jdbcTemplate.query("SELECT * FROM tokens LIMIT 1", new MapSqlParameterSource(), SingleColumnRowMapper.newInstance(String.class));
        Iterator<String> it = results.iterator();
        if (!it.hasNext())
            return null;

        String tokenJson = results.iterator().next();
        try {
            return new DefaultOAuth2AccessToken(mapper.readValue(tokenJson, OAuth2AccessToken.class));
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

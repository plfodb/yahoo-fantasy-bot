package com.pldfodb.client;

import com.pldfodb.controller.model.FantasyContentResource;
import com.pldfodb.controller.model.LeagueResource;
import com.pldfodb.controller.model.TransactionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class YahooClient {

    @Autowired private OAuth2RestTemplate oAuthTemplate;
    private RestTemplate restTemplate = new RestTemplate();
    private static String leagueId = "380.l.939571";

    public OAuth2AccessToken login() {
        return oAuthTemplate.getAccessToken();
    }

    public LeagueResource getLeague() {
        return oAuthTemplate.getForObject("https://fantasysports.yahooapis.com/fantasy/v2/league/" + leagueId, FantasyContentResource.class).getLeague();
    }

    public List<TransactionResource> getTransactions() {
        return oAuthTemplate.getForObject("https://fantasysports.yahooapis.com/fantasy/v2/league/380.l.939571/transactions;count=20", FantasyContentResource.class).getLeague().getTransactions();
    }
}

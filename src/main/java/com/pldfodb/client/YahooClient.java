package com.pldfodb.client;

import com.pldfodb.model.FantasyContentResource;
import com.pldfodb.model.LeagueResource;
import com.pldfodb.model.TransactionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YahooClient {

    @Autowired private OAuth2RestTemplate restTemplate;
    private static String leagueId = "380.l.939571";

    private String authorizationCode;

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public LeagueResource getLeague() {
        return restTemplate.getForObject("https://fantasysports.yahooapis.com/fantasy/v2/league/" + leagueId, FantasyContentResource.class).getLeague();
    }

    public List<TransactionResource> getTransactions() {
        return restTemplate.getForObject("https://fantasysports.yahooapis.com/fantasy/v2/league/380.l.939571/transactions;count=20", FantasyContentResource.class).getLeague().getTransactions();
    }
}

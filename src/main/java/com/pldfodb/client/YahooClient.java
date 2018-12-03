package com.pldfodb.client;

import com.pldfodb.controller.model.yahoo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class YahooClient {

    @Autowired protected RestTemplate restTemplate;
    private static final String LEAGUE_ID = "380.l.939571";
    private static final String BASE_URI = "https://fantasysports.yahooapis.com/fantasy/v2/";

    public YahooClient(RestTemplate template) {
        this.restTemplate = template;
    }

    public LeagueResource getLeague() {
        return restTemplate.getForObject(BASE_URI + LEAGUE_ID, FantasyContentResource.class).getLeague();
    }

    public List<TransactionResource> getTransactions(int count) {
        return restTemplate.getForObject(BASE_URI + LEAGUE_ID + "/transactions;count=" + String.valueOf(count), FantasyContentResource.class).getLeague().getTransactions();
    }

    public List<MatchupResource> getMatchups() {
        return restTemplate.getForObject(BASE_URI + LEAGUE_ID + "/scoreboard", FantasyContentResource.class).getLeague().getScoreboard().getMatchups();
    }
}

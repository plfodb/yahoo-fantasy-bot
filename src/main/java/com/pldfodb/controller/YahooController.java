package com.pldfodb.controller;

import com.pldfodb.model.FantasyContentResource;
import com.pldfodb.model.LeagueResource;
import com.pldfodb.model.TransactionResource;
import com.pldfodb.client.YahooClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/yahoo")
public class YahooController {

    @Autowired
    private YahooClient client;

//    @RequestMapping("/login")
//    public void login() {
//        new RestTemplate();
//    }

//    @RequestMapping(value = "/authorization_code", method = RequestMethod.GET)
//    public void setAuthorizationCode(@RequestParam("code") String code) {
//        client.setAuthorizationCode();
//    }

    @RequestMapping(value = "/league", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody LeagueResource getLeague() {

        return client.getLeague();
    }

    @RequestMapping(value = "/leagues", method = RequestMethod.POST)
    public void submitLeague(@RequestBody FantasyContentResource league) {

        return;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<TransactionResource> getTransactions() {

        return client.getTransactions();
    }
}

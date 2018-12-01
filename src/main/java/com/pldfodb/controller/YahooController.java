package com.pldfodb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pldfodb.client.YahooOAuthClient;
import com.pldfodb.controller.model.yahoo.FantasyContentResource;
import com.pldfodb.controller.model.yahoo.LeagueResource;
import com.pldfodb.controller.model.yahoo.TransactionResource;
import com.pldfodb.repo.AuthenticationRepository;
import com.pldfodb.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/yahoo")
public class YahooController {

    @Autowired private YahooOAuthClient client;
    @Autowired private AuthenticationRepository authRepo;
    @Autowired private SlackService slackService;

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public String redirect(Model model) {
//        return "yahoo-logged-in";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap map) throws JsonProcessingException {
        OAuth2AccessToken token = client.login();
        authRepo.updateToken(token);
        return "yahoo-logged-in ";
    }

    @RequestMapping(value = "/league", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody LeagueResource getLeague() {

        return client.getLeague();
    }

    @RequestMapping(value = "/leagues", method = RequestMethod.POST)
    public void submitLeague(@RequestBody FantasyContentResource league) {

        return;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<TransactionResource> getTransactions(@RequestParam(value = "count", defaultValue = "20") Integer count) {

        return client.getTransactions(count);
    }
}

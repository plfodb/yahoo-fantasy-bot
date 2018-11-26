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
@RequestMapping("/tokens")
public class OAuthTokenController {

    @Autowired private AuthenticationRepository authRepo;

    @PostMapping("/")
    public @ResponseBody void update(@RequestBody OAuth2AccessToken token) throws JsonProcessingException {
        authRepo.updateToken(token);
    }
}

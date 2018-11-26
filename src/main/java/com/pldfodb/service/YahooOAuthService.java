package com.pldfodb.service;

import com.pldfodb.client.YahooOAuthClient;
import com.pldfodb.repo.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.annotation.PostConstruct;

public abstract class YahooOAuthService {

    @Autowired protected AuthenticationRepository authRepo;
    @Autowired protected OAuth2ProtectedResourceDetails authDetails;
    protected YahooOAuthClient yahooClient;

    @PostConstruct
    public void setup() {
        System.out.println("initializing oauth client from db");
        OAuth2AccessToken token = authRepo.getToken();
        if (token != null) {
            yahooClient = new YahooOAuthClient(new OAuth2RestTemplate(authDetails, new DefaultOAuth2ClientContext(token)));
            System.out.println("Initialized yahoo client with stored OAuth credentials");
        }
        else
            System.out.println("No stored OAuth credentials found. User login required");
    }
}

package com.pldfodb.service;

import com.pldfodb.client.YahooOAuthClient;
import com.pldfodb.oauth.StaticExpirationOAuth2AccessToken;
import com.pldfodb.repo.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.logging.Logger;

public abstract class YahooOAuthService {

    @Autowired protected AuthenticationRepository authRepo;
    @Autowired protected OAuth2ProtectedResourceDetails authDetails;
    protected YahooOAuthClient yahooClient;

    private static final Logger LOGGER = Logger.getLogger(YahooOAuthService.class.getName());

    @PostConstruct
    public void setup() {
        setAuthentication();
        LOGGER.info("Initializing oauth client from db");
        OAuth2AccessToken token = authRepo.getToken();
        if (token != null) {
            yahooClient = new YahooOAuthClient(new OAuth2RestTemplate(authDetails, new DefaultOAuth2ClientContext(token)));
            LOGGER.info("Initialized yahoo client with stored OAuth credentials");
        }
        else
            LOGGER.info("No stored OAuth credentials found. User login required");
    }

    protected void setAuthentication() {

        if (SecurityContextHolder.getContext().getAuthentication() != null)
            return;

        // AccessTokenProviderChain to fix refresh. have to either set authentication or hack the clientOnly flag
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        });
    }
}

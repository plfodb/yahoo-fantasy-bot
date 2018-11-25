package com.pldfodb.client;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
public class YahooOAuthClient extends YahooClient {

    public YahooOAuthClient(OAuth2RestTemplate template) {
        super(template);
    }

    public OAuth2AccessToken login() {
        return ((OAuth2RestTemplate)restTemplate).getAccessToken();
    }
}

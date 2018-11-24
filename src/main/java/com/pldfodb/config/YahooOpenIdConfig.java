package com.pldfodb.config;

import com.pldfodb.oauth.ClientOnlyAuthorizationCodeDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
@PropertySource("classpath:yahoo.properties")
public class YahooOpenIdConfig {

    @Value("${client-id}")
    private String clientId;

    @Value("${client-secret}")
    private String clientSecret;

    @Value("${token-uri}")
    private String tokenUri;

    @Value("${authorization-uri}")
    private String userAuthorizationUri;

    @Value("${redirect-uri}")
    private String redirectUri;

    @Bean
    public OAuth2ProtectedResourceDetails yahooOpenId() {
        AuthorizationCodeResourceDetails details = new ClientOnlyAuthorizationCodeDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(tokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("openid"));
//        details.setPreEstablishedRedirectUri(redirectUri);
//        details.setUseCurrentUri(false);
        details.setUseCurrentUri(true);
        return details;
    }

    @Bean
    public OAuth2RestTemplate yahooOpenIdTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(yahooOpenId(), clientContext);
    }
}

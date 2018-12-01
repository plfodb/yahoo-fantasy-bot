package com.pldfodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:yahoo.properties")
public class YahooOAuthConfig {

    @Autowired
    public DataSource dataSource;

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
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(tokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("openid"));
        details.setUseCurrentUri(true);
        return details;
    }

//    @Bean
//    public ClientTokenServices clientTokenServices() {
//        return new JdbcClientTokenServices(dataSource);
//    }

    @Bean
    public OAuth2RestTemplate yahooOpenIdTemplate() {

        OAuth2RestTemplate template = new OAuth2RestTemplate(yahooOpenId(), new DefaultOAuth2ClientContext());
        return template;
    }
}

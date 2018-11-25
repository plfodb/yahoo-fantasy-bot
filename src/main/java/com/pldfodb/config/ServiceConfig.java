package com.pldfodb.config;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ServiceConfig {

//    @Value("${slack.token}")
//    private String token;
//
//    @Bean
//    public SlackSession slackSession() throws IOException {
//        SlackSession session = SlackSessionFactory.getSlackSessionBuilder(token).build();
//        session.connect();
//        return session;
//    }
}

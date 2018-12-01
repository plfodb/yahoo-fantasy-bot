package com.pldfodb.config;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@Getter
@PropertySource("classpath:slack.properties")
public class SlackConfig {

    @Value("${token}")
    private String token;

    @Value("${channel}")
    private String channel;

    @Bean
    public SlackSession slackSession() throws IOException {
        SlackSession session = SlackSessionFactory.getSlackSessionBuilder(token).build();
        session.connect();
        return session;
    }

    @Bean
    public SlackChannel slackChannel() throws IOException {
         return slackSession().findChannelByName(channel);
    }
}

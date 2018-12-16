package com.pldfodb.config;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.Getter;
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

    @Value("${channel.main}")
    private String mainChannel;

    @Value("${channel.matchups}")
    private String matchupsChannel;

    @Bean
    public SlackSession slackSession() throws IOException {
        SlackSession session = SlackSessionFactory.getSlackSessionBuilder(token).build();
        session.connect();
        return session;
    }

    @Bean(name = "MAIN_CHANNEL")
    public SlackChannel mainSlackChannel() throws IOException {
         return slackSession().findChannelByName(mainChannel);
    }

    @Bean(name = "MATCHUPS_CHANNEL")
    public SlackChannel matchupsSlackChannel() throws IOException {
        return slackSession().findChannelByName(matchupsChannel);
    }
}

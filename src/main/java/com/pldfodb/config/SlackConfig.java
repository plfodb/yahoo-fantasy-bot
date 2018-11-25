package com.pldfodb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource("classpath:slack.properties")
public class SlackConfig {

    @Value("${token}")
    private String token;
}

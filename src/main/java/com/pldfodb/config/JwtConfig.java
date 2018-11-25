package com.pldfodb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {

    @Value("${header:Authorization}")
    private String header;

    @Value("${prefix:Bearer }")
    private String prefix;

    @Value("${secret:JwtSecretKey}")
    private String secret;
}

package com.pldfodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YahooFantasyBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(YahooFantasyBotApplication.class, args);
	}
}

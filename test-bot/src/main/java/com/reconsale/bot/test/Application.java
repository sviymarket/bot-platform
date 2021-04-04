package com.reconsale.bot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public abstract class Application {
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}
}

package com.reconsale.bot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.reconsale.bot"
        //, "com.reconsale.viber4j"
})
public abstract class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

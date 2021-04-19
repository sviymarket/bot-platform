package com.reconsale.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    @Autowired
    private ViberStarter viberStarter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application started!");
        log.info("Setting viber webhook...");
        boolean registeredSuccessfully = viberStarter.registerBot();
        if (registeredSuccessfully) {
            log.info("Webhook set!");
        } else {
            log.error("Failed to set a webhook!");
        }


    }
}

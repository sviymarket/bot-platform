package com.reconsale.bot.test.response;

import com.reconsale.bot.integration.viber.AbstractResponseCase;
import com.reconsale.bot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class DefaultResponseCase extends AbstractResponseCase {

    @Override
    public Void provideResponse(Response response) {
        log.info("Provided response: " + response);
        log.info("Just logging...");
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return false;
    }

}

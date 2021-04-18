package com.reconsale.bot;

import com.reconsale.bot.integration.viber.ViberBotManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.reconsale.bot.constant.Properties.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;
import static com.reconsale.bot.constant.Properties.VIBER_BOT_WEBHOOK_URL_PROPERTY_REFERENCE;

@Slf4j
@Component
public class ViberStarter {

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    private String authToken;

    @Value(VIBER_BOT_WEBHOOK_URL_PROPERTY_REFERENCE)
    private String webhookUrl;

    @Autowired
    private ViberBotManager viberBotManager;

    public boolean registerBot() {
        return viberBotManager.viberBot(authToken).setWebHook(webhookUrl);
    }

}

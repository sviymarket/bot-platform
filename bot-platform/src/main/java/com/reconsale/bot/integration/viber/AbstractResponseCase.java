package com.reconsale.bot.integration.viber;

import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.bot.integration.Visualizer;
import com.reconsale.viber4j.ViberBotManager;
import com.reconsale.viber4j.keyboard.RichMedia;
import com.reconsale.viber4j.keyboard.ViberKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.reconsale.bot.constant.PropertiesNaming.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

    @Autowired
    protected ViberBotManager viberBotManager;

    @Autowired
    protected Visualizer<RichMedia, String, RichMedia, ViberKeyboard> viberVisualizer;

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    protected String botToken;

    @Autowired
    protected MessageSender messageSender;
}

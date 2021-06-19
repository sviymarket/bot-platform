package com.reconsale.test.bot.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.reconsale.test.bot.constant.MenuItems.*;

import java.util.Objects;

@Slf4j
@Component
public class ActivateBotResponseCase extends AbstractResponseCase {

    @Override
    public Object provideResponse(Response response) {

        log.info("activate response...");

        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);
            ViberKeyboard viberKeyboard = fromMenu(response.getMenu());
            viberBot.messageForUser(response.getUser().getId())
                    .postKeyboard(viberKeyboard);
        } else {
            log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && ACTIVATE_BOT.equals(response.getReference());
    }
}

package com.reconsale.bot.test.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.reconsale.bot.test.constant.MenuItems.MAIN_MENU;


@Slf4j
@Component
public class MainMenuResponseCase extends AbstractResponseCase {

    @Override
    public Object provideResponse(Response response) {

        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);
            ViberKeyboard viberKeyboard = fromMenu(response.getMenu());

            //String mainMenuMessage = getResourceBundle().getString(MAIN_MENU);
            viberBot.messageForUser(response.getUser().getId())
                    .postKeyboard(viberKeyboard);
                    //.postText(mainMenuMesage, viberKeyboard);
        } else {
            log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && MAIN_MENU.equals(response.getReference());
    }

}

package com.reconsale.bot.test.response;

import com.reconsale.bot.integration.viber.AbstractResponseCase;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.viber4j.ViberBot;
import com.reconsale.viber4j.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.reconsale.bot.test.constant.MessagesNaming.MAIN_MENU;


@Slf4j
@Component
public class NavigationResponseCase extends AbstractResponseCase {

    @Override
    public Object provideResponse(Response response) {
        //log.info("Provided response: " + response);
        //String text = viberVisualizer.createMessage(response.getRefefence(), response.getText());
        Menu menu = response.getMenu();
        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);
            //log.info("Sending navigation menu response: " + response);

            ViberKeyboard viberKeyboard = fromMenu(menu);
            String mainMenuMesage = getResourceBundle().getString(MAIN_MENU);
            viberBot.messageForUser(response.getUser().getId())
                    //.postText("Sending you to the main menu", viberKeyboard);
                    .postText(mainMenuMesage, viberKeyboard);
        } else {
            //log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && Objects.nonNull(response.getMenu());
    }

}

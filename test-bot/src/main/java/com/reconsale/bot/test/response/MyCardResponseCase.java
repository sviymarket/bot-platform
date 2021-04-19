package com.reconsale.bot.test.response;

import com.reconsale.bot.integration.viber.AbstractResponseCase;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.viber4j.ViberBot;
import com.reconsale.viber4j.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.reconsale.bot.test.constant.MessagesNaming.MY_CARD;


@Slf4j
@Component
public class MyCardResponseCase extends AbstractResponseCase {
    @Override
    public Object provideResponse(Response response) {

        Menu menu = response.getMenu();
        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);

            log.info("Sending navigation menu response: " + response);

            ViberKeyboard viberKeyboard = fromMenu(menu);
            String yourCardMessage = getResourceBundle().getString(MY_CARD);
            //viberBot.messageForUser(response.getUser().getId())
                //.postPicture("/resources/images/card.png", "card");

            viberBot.messageForUser(response.getUser().getId())
                //.postText("Sending you to the main menu", viberKeyboard);
                .postText(yourCardMessage + " 123123123", viberKeyboard);
        } else {
            log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && Objects.nonNull(response.getMenu())
                && (response.getMenu().getButtons().size() == 1);
    }
}

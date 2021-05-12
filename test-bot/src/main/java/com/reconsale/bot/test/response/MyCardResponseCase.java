package com.reconsale.bot.test.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.reconsale.bot.test.constant.MenuItems.MAIN_MENU;
import static com.reconsale.bot.test.constant.MenuItems.MY_CARD;


@Slf4j
@Component
public class MyCardResponseCase extends AbstractResponseCase {

    @Override
    public Object provideResponse(Response response) {

        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);
            ViberKeyboard viberKeyboard = fromMenu(response.getMenu());

            viberBot.messageForUser(response.getUser().getId())
                    .postPicture("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/UPC_A.svg/1200px-UPC_A.svg.png",
                            "card");

            String yourCardMessage = getResourceBundle().getString(MAIN_MENU);
            viberBot.messageForUser(response.getUser().getId())
                    //.postText("Sending you to the main menu", viberKeyboard);
                    .postText(yourCardMessage, viberKeyboard);
        } else {
            log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && MY_CARD.equals(response.getReference());
    }

}

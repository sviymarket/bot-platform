package com.reconsale.test.bot.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;
import static com.reconsale.test.bot.constant.MenuItems.MY_CARD;

import java.util.Objects;


@Slf4j
@Component
public class MyCardResponseCase extends AbstractResponseCase {

    @Override
    public Object provideResponse(Response response) {

        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = viberBotManager.viberBot(botToken);
            ViberKeyboard viberKeyboard = fromMenu(response.getMenu());

            viberBot.messageForUser(response.getUser().getId())
                    .postPicture("https://www.defit.org/wp-content/uploads/2013/07/barcode.jpg",
                            "Ваша карта клієнта",
                            "https://www.defit.org/wp-content/uploads/2013/07/barcode.jpg");

            viberBot.messageForUser(response.getUser().getId()).postKeyboard(viberKeyboard);
            /*
            String yourCardMessage = getResourceBundle().getString(MAIN_MENU);
            viberBot.messageForUser(response.getUser().getId())
                    //.postText("Sending you to the main menu", viberKeyboard);
                    .postText(yourCardMessage, viberKeyboard);
                    */
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

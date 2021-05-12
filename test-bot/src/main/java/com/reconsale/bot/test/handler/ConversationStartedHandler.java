package com.reconsale.bot.test.handler;

import com.google.gson.Gson;
import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.test.constant.MenuItems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.constant.HandlerKeys.CONVERSATION_STARTED_HANDLER_KEY;
import static com.reconsale.bot.test.constant.MenuItems.MAIN_MENU;


@Slf4j
@Component
public class ConversationStartedHandler implements Handler {

    @Override
    public Response handle(Request request) {
        // TODO: will contain registration or phone request flows
        ButtonStyle buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);
        Button b1 = Button.navigationButton(MenuItems.MY_CARD, buttonStyle);
        Button b2 = Button.navigationButton(MenuItems.MY_BONUSES, buttonStyle);
        Button b3 = Button.navigationButton(MenuItems.PROMOTIONS, buttonStyle);
        Button b4 = Button.navigationButton(MenuItems.MY_BUYS, buttonStyle);
        Button b5 = Button.navigationButton(MenuItems.CONTACT_US, buttonStyle);
        Button b6 = Button.navigationButton(MenuItems.INVITE_FRIEND, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Arrays.asList(b1, b2, b3, b4, b5, b6));

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(MAIN_MENU)
                .build();
    }

    @Override
    public String mapping() {
        return CONVERSATION_STARTED_HANDLER_KEY;
    }
}

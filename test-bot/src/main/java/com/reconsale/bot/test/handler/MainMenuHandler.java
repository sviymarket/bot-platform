package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.test.constant.MenuItems;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.bot.test.constant.MenuItems.MAIN_MENU;


@Component
public class MainMenuHandler implements Handler {

    @Override
    public Response handle(Request request) {
        ButtonStyle buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);
        Button b1 = new Button(MenuItems.MY_CARD, buttonStyle);
        Button b2 = new Button(MenuItems.MY_BONUSES, buttonStyle);
        Button b3 = new Button(MenuItems.PROMOTIONS, buttonStyle);
        Button b4 = new Button(MenuItems.MY_BUYS, buttonStyle);
        Button b5 = new Button(MenuItems.CONTACT_US, buttonStyle);
        Button b6 = new Button(MenuItems.INVITE_FRIEND, buttonStyle);

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
        return PRESSED + ":" + MAIN_MENU;
    }
}

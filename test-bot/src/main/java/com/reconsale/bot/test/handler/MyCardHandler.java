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
import static com.reconsale.bot.test.constant.MenuItems.MY_CARD;


@Component
public class MyCardHandler implements Handler {
    @Override
    public Response handle(Request request) {
        ButtonStyle buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);
        Button b1 = new Button(MenuItems.MAIN_MENU, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Arrays.asList(b1));

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(MY_CARD)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + MY_CARD;
    }
}

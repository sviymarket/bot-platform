package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;

@Component
public class MainMenuHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());

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

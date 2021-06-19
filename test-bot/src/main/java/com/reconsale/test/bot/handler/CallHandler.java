package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Menu;
import org.springframework.stereotype.Component;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.CALL;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;

@Component
public class CallHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .text("Будемо раді Вам допомогти! Номер гарячої лінії: +380ХХХХХХХХХХ")
                .reference(MAIN_MENU)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + CALL;
    }

}

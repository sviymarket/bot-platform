package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.Basket;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;

@Component
public class CleanHandler implements Handler {

    @Override
    public Response handle(Request request) {
        String preorderMessage = "Передзамовлення оформлено, дякуємо!";
        Application.baskets.put(request.getUser().getId(), new Basket());

        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());

        return Response.builder()
                .user(request.getUser())
                .text(preorderMessage)
                .menu(menu)
                .reference(MAIN_MENU)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + "empty_list";
    }

}

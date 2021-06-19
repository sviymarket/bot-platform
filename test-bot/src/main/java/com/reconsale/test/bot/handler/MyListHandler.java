package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.Basket;
import com.reconsale.test.bot.Product;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.MY_BUYS;

@Component
public class MyListHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Menu menu = new Menu();
        List<String> productNames = loadListFromBasket(request.getUser().getId());
        List<Button> buttons = new ArrayList<>();
        if (!productNames.isEmpty()) {
            buttons.addAll(ButtonListUtil.createMenuButtonsFromList(productNames));
            buttons.add(ButtonListUtil.createNoElementsInListButton());
        } else {
            buttons.add(ButtonListUtil.createNoElementsInListButton());
        }
        buttons.add(ButtonListUtil.createBackToMainMenuButton());
        menu.setButtons(buttons);
        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(MY_BUYS)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + MY_BUYS;
    }

    private List<String> loadListFromBasket(String user) {
        Basket basket = Application.baskets.get(user);
        if (basket != null) {
            return basket.getProducts().stream().map(Product::getName).collect(Collectors.toList());
        } else {
            return Arrays.asList("Fairy Professional - миючий засіб", "Head&Shoulders - шампунь");
            //return Collections.emptyList();
        }
    }

}

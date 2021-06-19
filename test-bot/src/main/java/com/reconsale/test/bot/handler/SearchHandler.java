package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.constant.GlobalColors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;
import static com.reconsale.test.bot.constant.MenuItems.SEARCH;

@Component
public class SearchHandler implements Handler {

    private ButtonStyle buttonStyle_listItem = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);

    @Override
    public Response handle(Request request) {
        List<String> intents = Arrays.asList("Як мити підлогу?", "Як помити ванну?", "Як позбутись накипів на каструлях?");
        List<Button> buttons = createMenuButtonsFromList(intents);
        buttons.add(ButtonListUtil.createBackToMainMenuButton());

        Menu menu = new Menu();
        menu.setButtons(buttons);

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(SEARCH)
                .build();
    }

    public void addProduct(String user, String product) {
        Application.baskets.get(user).add(Application.getProduct(product));
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + SEARCH;
    }


    private List<Button> createMenuButtonsFromList(List<String> productNames) {
        List<Button> buttons = new ArrayList<>();
        for (String productName : productNames) {
            Button b = Button.navigationButton(productName, buttonStyle_listItem);
            buttons.add(b);
        }
        return buttons;
    }
}

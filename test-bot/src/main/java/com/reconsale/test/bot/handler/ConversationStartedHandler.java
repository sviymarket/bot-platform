package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonActionType;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.Basket;
import com.reconsale.test.bot.Product;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.reconsale.bot.constant.HandlerKeys.CONVERSATION_STARTED_HANDLER_KEY;
import static com.reconsale.bot.model.constant.Buttons.DEFAULT_BUTTON_WIDTH;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;
import static com.reconsale.test.bot.constant.MenuItems.PERSONAL_PROPOSALS;


@Slf4j
@Component
public class ConversationStartedHandler implements Handler {

    @Override
    public Response handle(Request request) {
        log.info("main menu case...");

        if (request.getPayload().getContent() == null) {
            Application.baskets.put(request.getUser().getId(), new Basket());
            return mainMenuResponse(request);
        } else {
            if ("мити підлогу".equalsIgnoreCase(request.getPayload().getContent()) || "{\"data\":\"pressed:Як мити підлогу?\"}".equalsIgnoreCase(request.getPayload().getContent())) {
                return resolveSearch(request.getUser());
            }
            if (request.getPayload().getContent().contains("viber://forward?text=")) {
                Response response = mainMenuResponse(request);
                return response;
            }
            if (request.getPayload().getContent().contains("addToCart")) {
                Response response = mainMenuResponse(request);
                response.setText("Товар додано до списку!");
                return response;
            } else {
                Response response = mainMenuResponse(request);
                response.setText("Нажаль немає результатів");
                return response;
            }
        }
    }

    private Response resolveSearch(User user) {
        // TODO ISmail Auto-generated method stub
        log.info("display products for floor cleaning");
        ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, DEFAULT_BUTTON_WIDTH);
        Button mainMenuButton = Button.navigationButton(MenuItems.MAIN_MENU, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Collections.singletonList(mainMenuButton));

        return Response.builder()
                .user(user)
                .tiles(getProductTilesForFloorCleaning())
                .menu(menu)
                .reference(PERSONAL_PROPOSALS)
                .build();
        /*
        Menu menu = new Menu();
        List<String> productNames = ;
        List<Button> buttons = new ArrayList<>();
        if (!productNames.isEmpty()) {
            buttons.addAll(ButtonListUtil.createMenuButtonsFromList(productNames));
        } else {
            buttons.add(ButtonListUtil.createNoElementsInListButton());
        }
        buttons.add(ButtonListUtil.createBackToMainMenuButton());
        menu.setButtons(buttons);
        return Response.builder()
                .user(user)
                .menu(menu)
                .reference(MY_BUYS)
                .build();
        */
    }

    private List<Tile> getProductTilesForFloorCleaning() {
        ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);
        List<Tile> tiles = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cilit Bank", "https://content.rozetka.com.ua/goods/images/big/10717184.jpg", "150"));
        products.add(new Product("Ms. Proper", "https://content2.rozetka.com.ua/goods/images/big/10689731.png", "45"));

        for (Product product : products) {
            Tile tile = new Tile();
            tile.setContentImage(product.getImage());
            tile.setButton(new Button(PERSONAL_PROPOSALS, "addToCart", ButtonActionType.REPLY, buttonStyle));
            tile.setText(product.getName() + " (ціна=" + product.getPrice() + "грн)");
            tile.setBottomText(product.getName());
            tiles.add(tile);
        }
        return tiles.stream().limit(3).collect(Collectors.toList());
    }

    @Override
    public String mapping() {
        return CONVERSATION_STARTED_HANDLER_KEY;
    }

    private Response mainMenuResponse(Request request) {
        // TODO: will contain registration or phone request flows
        ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);
        Button b1 = Button.navigationButton(MenuItems.MY_CARD, buttonStyle);
        Button b2 = Button.navigationButton(MenuItems.MY_BONUSES, buttonStyle);
        Button b3 = Button.navigationButton(MenuItems.PERSONAL_PROPOSALS, buttonStyle);
        Button b4 = Button.navigationButton(MenuItems.MY_BUYS, buttonStyle);
        Button b5 = Button.navigationButton(MenuItems.SEARCH, buttonStyle);
        Button b6 = Button.navigationButton(MenuItems.INVITE_FRIEND, buttonStyle);

        Button b7 = Button.navigationButton(MenuItems.CALL, buttonStyle);
        Button b8 = Button.navigationButton(MenuItems.LOCATION, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8));

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(MAIN_MENU)
                .build();
    }

}

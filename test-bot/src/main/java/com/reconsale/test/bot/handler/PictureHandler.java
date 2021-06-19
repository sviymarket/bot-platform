package com.reconsale.test.bot.handler;

import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;
import static com.reconsale.test.bot.constant.MenuItems.PERSONAL_PROPOSALS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonActionType;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.Product;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

@Component
public class PictureHandler implements Handler {

    private ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);
    
    
    @Override
    public Response handle(Request request) {
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
                .tiles(getProductTiles())
                .reference(MAIN_MENU)
                .build();
    }
    
    private List<Tile> getProductTiles() {
        List<Tile> tiles = new ArrayList<>();
        Product product = Application.products.get(0);
            Tile tile = new Tile();
            tile.setContentImage(product.getImage());
            tile.setButton(new Button(PERSONAL_PROPOSALS, "addToCart", ButtonActionType.REPLY, buttonStyle));
            tile.setText(product.getName() + " (ціна=" + product.getPrice() + "грн)");
            tile.setBottomText(product.getName());
            tiles.add(tile);
        
        return tiles.stream().limit(1).collect(Collectors.toList());
    }

    @Override
    public String mapping() {
        return "picture-message";
    }

}

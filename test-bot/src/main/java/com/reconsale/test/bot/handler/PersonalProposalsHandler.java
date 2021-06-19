package com.reconsale.test.bot.handler;

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

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.reconsale.bot.model.constant.Buttons.DEFAULT_BUTTON_WIDTH;
import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.PERSONAL_PROPOSALS;


@Component
public class PersonalProposalsHandler implements Handler {

    private ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);

    @Override
    public Response handle(Request request) {
        buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, DEFAULT_BUTTON_WIDTH);

        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());

        return Response.builder()
                .user(request.getUser())
                .tiles(getProductTiles())
                .text("Персональна пропозиція!")
                .menu(menu)
                .reference(PERSONAL_PROPOSALS)
                .build();
    }

    public void addProduct(String user, String product) {
        Application.baskets.get(user).add(Application.getProduct(product));
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + PERSONAL_PROPOSALS;
    }

    private List<Tile> getProductTiles() {
        List<Tile> tiles = new ArrayList<>();
        for (Product product : Application.products) {
            Tile tile = new Tile();
            tile.setContentImage(product.getImage());
            tile.setButton(new Button(PERSONAL_PROPOSALS, "addToCart", ButtonActionType.REPLY, buttonStyle));
            tile.setText(product.getName() + " (ціна=" + product.getPrice() + "грн)");
            tile.setBottomText(product.getName());
            tiles.add(tile);
        }
        return tiles.stream().limit(3).collect(Collectors.toList());
    }
}

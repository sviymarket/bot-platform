package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.constant.MenuItems;

import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.model.constant.Buttons.DEFAULT_BUTTON_WIDTH;
import static com.reconsale.bot.model.constant.Buttons.PRESSED;
/*
@Component
public class PromotionsHandler implements Handler {

    private static final String PROMOTIONS_URL = "https://sviymarket.com/aktsi-502/";

    @Override
    public Response handle(Request request) {
        ButtonStyle buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);

        Tile t1 = new Tile();
        t1.setContentImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        t1.setButton(new Button(MenuItems.PERSONAL_PROPOSALS, null, null, buttonStyle));
        t1.setTileUrl(PROMOTIONS_URL);

        Tile t2 = new Tile();
        t2.setContentImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        t2.setButton(new Button(MenuItems.PERSONAL_PROPOSALS, null, null, buttonStyle));
        t2.setTileUrl("https://sviymarket.com/aktsi-502/");

        Tile t3 = new Tile();
        t3.setContentImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        t3.setButton(new Button(MenuItems.PERSONAL_PROPOSALS, null, null, buttonStyle));
        t3.setTileUrl("https://sviymarket.com/aktsi-502/");

        Tile t4 = new Tile();
        t4.setContentImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        t4.setButton(new Button(MenuItems.PERSONAL_PROPOSALS, null, null, buttonStyle));
        t4.setTileUrl("https://sviymarket.com/aktsi-502/");

        Tile t5 = new Tile();
        t5.setContentImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        t5.setButton(new Button(MenuItems.PERSONAL_PROPOSALS, null, null, buttonStyle));
        t5.setTileUrl("https://sviymarket.com/aktsi-502/");

        buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, DEFAULT_BUTTON_WIDTH);
        Button b11 = Button.navigationButton(MenuItems.MAIN_MENU, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Arrays.asList(b11));


        return Response.builder()
                .user(request.getUser())
                .tiles(Arrays.asList(t1, t2, t3, t4, t5))
                .menu(menu)
                .reference(MenuItems.PERSONAL_PROPOSALS)
                .build();
    }
    
    @Override
    public String mapping() {
        return PRESSED + ":" + MenuItems.PERSONAL_PROPOSALS;
    }
}
*/
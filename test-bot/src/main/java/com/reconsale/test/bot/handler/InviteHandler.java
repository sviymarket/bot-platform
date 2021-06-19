package com.reconsale.test.bot.handler;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.INVITE_FRIEND;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;
import static com.reconsale.test.bot.constant.MenuItems.MY_CARD;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

@Component
public class InviteHandler implements Handler {

    @Override
    public Response handle(Request request) {

        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());

        Tile t3 = new Tile();
        t3.setText("Натисни сюди щоб запросити друга в чат-бот!");
        t3.setContentImage("https://i.pinimg.com/originals/d2/ea/d8/d2ead876ae76ba7147f68e7d2417c5f3.png");
        t3.setButton(new Button());
        t3.setTileUrl("viber://forward?text=viber://pa?chatURI=chustenkobot");

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .tiles(Arrays.asList(t3))
                .reference(INVITE_FRIEND)
                .build();

        /*
        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .reference(INVITE_FRIEND)
                .build();
                */
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + INVITE_FRIEND;
        
    }

}

package com.reconsale.test.bot.handler;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.test.bot.constant.MenuItems.LOCATION;
import static com.reconsale.test.bot.constant.MenuItems.MAIN_MENU;

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
public class LocationHandler implements Handler {

    @Override
    public Response handle(Request request) {
        ButtonStyle buttonStyle = new ButtonStyle("#e6e6e6", ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);

        Menu menu = new Menu();
        menu.setButtons(MainButtonsUtil.mainMenuButtons());
        
        Tile t3 = new Tile();
        t3.setContentImage("https://i.pinimg.com/originals/77/3c/d2/773cd29e6f4e29cf1ed4af4b9b3b854d.png");
        t3.setText("Наші магазини знаходяться тут!");
        t3.setButton(new Button("asdfdsf", "abc", null, buttonStyle));
        t3.setTileUrl("https://i.pinimg.com/originals/77/3c/d2/773cd29e6f4e29cf1ed4af4b9b3b854d.png");

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .tiles(Arrays.asList(t3))
                .reference(MAIN_MENU)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + LOCATION;
        
    }

}

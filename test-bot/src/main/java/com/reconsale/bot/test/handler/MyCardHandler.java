package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonAction;
import com.reconsale.bot.model.response.Menu;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.constant.HandlerKeys.*;

@Component
public class MyCardHandler implements Handler {
    @Override
    public Response handle(Request request) {
        // TODO: move to constants
        String mainMenuButtonId = "main_menu";
        Button b1= new Button("main_menu", "Main Menu", new ButtonAction("pressed:" + mainMenuButtonId));

        Menu menu = new Menu("template", Arrays.asList(
                b1));

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + "my_card";
    }
}

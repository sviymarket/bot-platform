package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonAction;
import com.reconsale.bot.model.response.Menu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.constant.HandlerKeys.CONVERSATION_STARTED_HANDLER_KEY;

@Slf4j
@Component
public class ConversationStartedHandler implements Handler {
    @Override
    public Response handle(Request request) {
        // TODO: move to constants
        String myCardId = "my_card";
        Button b1 = new Button(myCardId, "My Card", new ButtonAction("pressed:" + myCardId));
        String promotionsId = "promotions";
        Button b2 = new Button(promotionsId, "Promotions", new ButtonAction("pressed:" + promotionsId));
        String buyHistoryId = "buy_history";
        Button b3 = new Button(buyHistoryId, "Buy history", new ButtonAction("pressed:" + buyHistoryId));
        String storesId = "stores";
        Button b4 = new Button(storesId, "Stores", new ButtonAction("pressed:" + storesId));
        String contactUsId = "contact_us";
        Button b5 = new Button(contactUsId, "Contact Us", new ButtonAction("pressed:" + contactUsId));
        String ourWebpageId = "our_webpage";
        Button b6 = new Button(ourWebpageId, "Our Webpage", new ButtonAction("pressed:" + ourWebpageId));

        Menu menu = new Menu("template?", Arrays.asList(
                b1, b2, b3, b4, b5, b6));

        return Response.builder()
                .user(request.getUser())
                .menu(menu)
                .build();
    }

    @Override
    public String mapping() {
        return CONVERSATION_STARTED_HANDLER_KEY;
    }
}

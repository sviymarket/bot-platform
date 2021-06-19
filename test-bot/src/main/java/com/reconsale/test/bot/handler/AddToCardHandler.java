package com.reconsale.test.bot.handler;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;

import com.google.gson.Gson;
import com.reconsale.bot.model.response.ButtonAction;
import com.reconsale.test.bot.Application;
import com.reconsale.test.bot.Basket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;

@Slf4j
@Component
public class AddToCardHandler implements Handler {

    @Override
    public Response handle(Request request) {
        String userId = request.getUser().getId();
        String content = request.getPayload().getContent();
        ButtonAction buttonAction = (new Gson()).fromJson(content, ButtonAction.class);
        String product = buttonAction.getData().substring(buttonAction.getData().indexOf("|"));
        product = product.replace("|", "");
        addProduct(userId, product);
        log.info("basket updated for user={}, products={}", userId, Application.baskets.get(userId).toString());
        return null;
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + "addToCard";
    }

    public void addProduct(String user, String product) {
        Application.baskets.putIfAbsent(user, new Basket());
        Application.baskets.get(user).add(Application.getProduct(product));
    }
}

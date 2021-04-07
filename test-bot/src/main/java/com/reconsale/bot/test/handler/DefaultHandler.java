package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.handler.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import org.springframework.stereotype.Component;

@Component
public class DefaultHandler implements Handler {

    @Override
    public Response handle(Request request) {
        return Response.builder().text("Hello World").build();
    }

    @Override
    public String mapping() {
        return "*";
    }

}

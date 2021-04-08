package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler implements Handler {

    @Override
    public Response handle(Request request) {
        return Response.builder()
                .user(request.getUser())
                .text(request.getPayload().getContent())
                .build();
    }

    @Override
    public String mapping() {
        return "text";
    }

}

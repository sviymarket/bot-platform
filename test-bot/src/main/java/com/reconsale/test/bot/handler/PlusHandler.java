package com.reconsale.test.bot.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;

@Slf4j
@Component
public class PlusHandler implements Handler {
    @Override
    public Response handle(Request request) {
        log.info("plus activated");

        return Response.builder()
                .user(request.getUser())
                .text("+1 product")
                .build();
    }


    @Override
    public String mapping() {
        return PRESSED + ":" + "plus";
    }

}

package com.reconsale.bot.engine.handler;

import com.reconsale.bot.integration.viber.MessageSender;
import com.reconsale.bot.model.Payload;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.User;
import com.reconsale.bot.model.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TextMessageHandler implements Handler {

    @Autowired
    private MessageSender messageSender;

    @Override
    public Response handle(Request request) {
        User user = request.getUser();
        Payload payload = request.getPayload();
        if (Objects.nonNull(payload.getContent()) && Objects.nonNull(user)) {
            String senderId = user.getId();
            String receivedText = payload.getContent();
            messageSender.sendTextMessage("You've just sent '" + receivedText + "'", senderId);
        }
        return null;
    }

    @Override
    public String mapping() {
        return EventType.MESSAGE.getDescription();
    }
}

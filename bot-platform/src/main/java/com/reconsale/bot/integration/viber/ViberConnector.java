package com.reconsale.bot.integration.viber;

import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.bot.model.Payload;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.User;
import com.reconsale.viber4j.incoming.Incoming;
import com.reconsale.viber4j.incoming.IncomingImpl;
import lombok.extern.slf4j.Slf4j;
import org.spark_project.guava.collect.Sets;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
public class ViberConnector extends Connector {

    private final String MESSAGE_EVENT = "message";
    private final String START_MSG_EVENT = "conversation_started";

    private final Set<String> acceptedEventTypes = Sets.newHashSet(MESSAGE_EVENT, START_MSG_EVENT);

    @RequestMapping(method = RequestMethod.POST, path = "/api/viber/webhook")
    public void callback(@RequestBody String inputString) {
        log.info("Received input: " + inputString);
        Incoming incoming = IncomingImpl.fromString(inputString);
        log.info("Deserialized into: " + incoming);

        String eventType = incoming.getEvent();
        if (!acceptedEventTypes.contains(eventType)) {
            log.info("Skipping event type '" + eventType + "'...");
            return;
        }

        Request request = resolveRequest(incoming);
        Response response = dispatcher.dispatch(request);

        resolveResponse(response);
    }

    private Request resolveRequest(Incoming incoming) {
        User user = new User(incoming.getSenderId());
        String requestId = UUID.randomUUID().toString();
        Payload payload = new Payload();
        payload.setContent(incoming.getMessageText());
        payload.setContentType(incoming.getMessageType());
        return new Request(requestId, user, null, payload);
    }

    private ResponseEntity<?> resolveResponse(Response response) {
        for (ResponseCase<?> responseCase : responseCases) {
            if (responseCase.evaluate(response)) {
                responseCase.provideResponse(response);
                return ResponseEntity.ok().build();
            }
        }

        throw new IllegalStateException("Response case is not handled");
    }

}

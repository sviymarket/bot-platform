package com.reconsale.bot.integration.viber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.bot.model.*;
import com.reconsale.bot.model.request.Context;
import com.reconsale.bot.model.request.Payload;
import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.viber.input.WebhookRequestPayload;
import lombok.extern.slf4j.Slf4j;
import org.spark_project.guava.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String MESSAGE = "message";
    private static final String CONVERSATION_STARTED = "conversation_started";

    private final Set<String> handledEventTypes = Sets.newHashSet(MESSAGE, CONVERSATION_STARTED);

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.POST, path = "/api/viber/webhook")
    public void callback(@RequestBody String inputString) {
        log.info("Received input: " + inputString);
        WebhookRequestPayload webhookRequestPayload = fromString(inputString);
        log.debug("Deserialized into: " + webhookRequestPayload);

        String eventType = webhookRequestPayload.getEvent();
        if (!handledEventTypes.contains(eventType)) {
            log.info("Skipping event type '" + eventType + "'...");
            return;
        }

        Request request = resolveRequest(webhookRequestPayload);
        Response response = requestDispatcher.dispatch(request);

        resolveResponse(response);
    }

    private WebhookRequestPayload fromString(String inputString) {
        WebhookRequestPayload webhookRequestPayload = null;
        try {
            webhookRequestPayload = objectMapper.readValue(inputString, WebhookRequestPayload.class);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize input string1: " + inputString);
            log.error(e.getMessage());
        }
        return webhookRequestPayload;
    }

    private Request resolveRequest(WebhookRequestPayload webhookRequestPayload) {

        String requestId = UUID.randomUUID().toString();

        User user = new User(webhookRequestPayload.getUser().getId());

        Context context = null;

        Payload payload = new Payload();
        payload.setEventType(webhookRequestPayload.getEvent());

        if (Objects.nonNull(webhookRequestPayload.getMessage())) {
            payload.setContent(webhookRequestPayload.getMessage().getText());
            payload.setContentType(webhookRequestPayload.getMessage().getType());
        }

        Request request = new Request(requestId, user, context, payload);
        log.debug("Resolved request: " + request);
        return request;
    }

    private ResponseEntity<?> resolveResponse(Response response) {
        log.debug("Resolving response: " + response);
        for (ResponseCase<?> responseCase : responseCases) {
            if (responseCase.evaluate(response)) {
                log.info("Evaluated by: " + responseCase.getClass());
                responseCase.provideResponse(response);
                return ResponseEntity.ok().build();
            }
        }

        throw new IllegalStateException("Response case is not handled");
    }

}

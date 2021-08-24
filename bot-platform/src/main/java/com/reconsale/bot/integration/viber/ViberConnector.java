package com.reconsale.bot.integration.viber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.reconsale.bot.engine.ResponseCase;
import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.request.Context;
import com.reconsale.bot.model.request.Payload;
import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.viber.input.Message;
import com.reconsale.bot.model.viber.input.WebhookRequestPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.reconsale.bot.constant.EventTypes.CONVERSATION_STARTED;
import static com.reconsale.bot.model.viber.utils.ViberConstants.MESSAGE;

@Slf4j
@RestController
public class ViberConnector extends Connector {

    private final Set<String> handledEventTypes = Sets.newHashSet(MESSAGE, CONVERSATION_STARTED);

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.POST, path = "/api/viber/webhook")
    public void callback(@RequestBody String inputString) {
        log.debug("Received input: " + inputString);
        WebhookRequestPayload webhookRequestPayload = fromString(inputString);
        log.debug("Deserialized into: " + webhookRequestPayload);
        if (Objects.isNull(webhookRequestPayload)) {
            log.error("Failed to deserialize input string! {}", inputString);
            log.error("Returning...");
            return;
        }

        String eventType = webhookRequestPayload.getEvent();
        if (!handledEventTypes.contains(eventType)) {
            log.debug("Skipping event type '" + eventType + "'...");
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
            log.error("Failed to deserialize input string: " + inputString);
            log.error(e.getMessage());
        }
        return webhookRequestPayload;
    }

    private Request resolveRequest(WebhookRequestPayload webhookRequestPayload) {
        String requestId = UUID.randomUUID().toString();
        User user = new User(webhookRequestPayload.getUser().getId());
        Context context = null;

        if (StringUtils.isNotBlank(webhookRequestPayload.getContext())) {
            context = new Context();
            Map<String, String> data = new HashMap<>();
            data.put("context", webhookRequestPayload.getContext());
            context.setData(data);
        }

        Payload payload = new Payload();
        payload.setEventType(webhookRequestPayload.getEvent());

        if (Objects.nonNull(webhookRequestPayload.getMessage())) {
            Message message = webhookRequestPayload.getMessage();
            payload.setContent(message.getText());
            payload.setContentType(message.getType());

            if (Objects.nonNull(message.getContact())) {
                user.setPhoneNumber(message.getContact().getPhoneNumber());
            }
        }

        Request request = new Request(requestId, user, context, payload);
        log.debug("Resolved request: " + request);
        return request;
    }

    private ResponseEntity<?> resolveResponse(Response response) {
        log.debug("Resolving response: " + response);
        for (ResponseCase<?> responseCase : responseCases) {
            if (responseCase.evaluate(response)) {
                log.debug("Evaluated by: " + responseCase.getClass());
                responseCase.provideResponse(response);
                return ResponseEntity.ok().build();
            }
        }
        //throw new IllegalStateException("Response case is not handled");

        // Should be unreachable
        log.info("Could not find Response Case for Response: " + response);
        return null;
    }

}

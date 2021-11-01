package com.reconsale.bot.integration.viber;

import static com.reconsale.bot.constant.EventTypes.CONVERSATION_STARTED;
import static com.reconsale.bot.constant.EventTypes.MESSAGE;
import static com.reconsale.bot.constant.EventTypes.SUBSCRIBED;
import static com.reconsale.bot.constant.EventTypes.UNSUBSCRIBED;
import static com.reconsale.bot.constant.EventTypes.DELIVERED;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.reconsale.bot.engine.ResponseCase;
import com.reconsale.bot.engine.SystemHandler;
import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.request.Context;
import com.reconsale.bot.model.request.Payload;
import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.viber.input.Message;
import com.reconsale.bot.model.viber.input.WebhookRequestPayload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ViberConnector extends Connector implements ApplicationContextAware {

    private final Set<String> handledEventTypes = Sets.newHashSet(MESSAGE, CONVERSATION_STARTED);
    private final Set<String> systemEventTypes = Sets.newHashSet(SUBSCRIBED, UNSUBSCRIBED, DELIVERED);

    private Map<String, SystemHandler> systemHandlers;    

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
        
        if (systemEventTypes.contains(eventType)) {
            SystemHandler systemHandler = systemHandlers.get(eventType);
            
            if (systemHandler != null) {
               log.info("Handling system event: " + eventType + " for user " +webhookRequestPayload.getUserId());
               systemHandler.handle(resolveRequest(webhookRequestPayload));
            } else {
                log.info("Not found handler for system event: " + eventType + webhookRequestPayload.getUserId()); 
            }
        }       
        
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
        String userId = null;
        
        if (webhookRequestPayload.getUser() != null) {
            userId = webhookRequestPayload.getUser().getId();
        } else {
            userId = webhookRequestPayload.getUserId();
        }
        
        User user = new User(userId);
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

        Request request = new Request(requestId, user, context, payload, webhookRequestPayload.getMessageToken());
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        systemHandlers = new HashMap<String, SystemHandler>();
        for (Map.Entry<String, SystemHandler> e : applicationContext.getBeansOfType(SystemHandler.class).entrySet()) {
            systemHandlers.put(e.getValue().getEvent(), e.getValue());
        }        
    }
}

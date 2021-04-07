package com.reconsale.bot.integration.viber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.model.Context;
import com.reconsale.bot.model.Payload;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.User;
import com.reconsale.bot.model.viber.Sender;
import com.reconsale.bot.model.viber.WebhookPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/viber")
public class ViberConnector extends Connector {

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.POST, path = "/webhook")
    public void callback(@RequestBody String inputString) {

        log.info("Received input: " + inputString);

        WebhookPayload webhookPayload = toWebhookPayload(inputString);

        log.info("Deserialzied as: " + webhookPayload);

        Request request = resolveRequest(webhookPayload);
        dispatcher.dispatch(request);
    }

    private WebhookPayload toWebhookPayload(String string) {
        WebhookPayload webhookPayload = null;
        try {
            webhookPayload = objectMapper.readValue(string, WebhookPayload.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.info("Failed to deserialize input: " + string);
        }
        return webhookPayload;
    }

    private Request resolveRequest(WebhookPayload webhookPayload) {
        Sender sender = webhookPayload.getSender();
        User user = null;
        if (Objects.nonNull(sender)) {
            user = new User(sender.getId());
        }
        Payload payload = null;
        if (Objects.nonNull(webhookPayload.getMessage())) {
            payload = new Payload(
                    webhookPayload.getMessage().getText(),
                    webhookPayload.getMessage().getType(),
					null, null);
        }

        // TODO: add context
        //Context context = contextManager.getContext(user.getId());
        return new Request(user, UUID.randomUUID().toString(), new Context(), payload);
    }

}

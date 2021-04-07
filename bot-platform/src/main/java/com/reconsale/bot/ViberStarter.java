package com.reconsale.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.reconsale.bot.model.viber.RegisterWebhookRequest;
import com.reconsale.viber4j.ViberBot;
import com.reconsale.viber4j.ViberBotManager;
import com.reconsale.viber4j.utils.ViberConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reconsale.bot.constant.PropertiesNaming.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;
import static com.reconsale.bot.constant.PropertiesNaming.VIBER_BOT_WEBHOOK_URL_PROPERTY_REFERENCE;

@Slf4j
@Component
public class ViberStarter {

    private static final String TOKEN_HEADER = "X-Viber-Auth-Token";
    private static final String REGISTER_WEBHOOK_URL = "https://chatapi.viber.com/pa/set_webhook";

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    private String authToken;

    @Value(VIBER_BOT_WEBHOOK_URL_PROPERTY_REFERENCE)
    private String webhookUrl;

    @Autowired
    private ViberBotManager viberBotManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void registerBot() {
        viberBotManager.viberBot(authToken);
                //.setWebHook(webhookUrl);
        registerWebHook();
    }

    private void registerWebHook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(TOKEN_HEADER, authToken);

        JsonObject message = new JsonObject();
        message.addProperty("url", webhookUrl);
        JsonArray eventsArray = new JsonArray();
        Stream.of(ViberBot.CallbackEvent.values()).forEach(
                viberEvent -> eventsArray.add(viberEvent.name()));
        message.add(ViberConstants.EVENT_TYPES, eventsArray);

        RegisterWebhookRequest r = new RegisterWebhookRequest();
        r.setUrl(webhookUrl);
        List<String> eventTypes = Arrays.asList(ViberBot.CallbackEvent.values()).stream()
                .map(ViberBot.CallbackEvent::name)
                .collect(Collectors.toList());
        r.setEventTypes(eventTypes);


        String requestString = null;
        try {
            requestString = objectMapper.writeValueAsString(r);
            log.info("Reuqest string: " + requestString);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize request! " + r);
        }

        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        String result = restTemplate.postForObject(REGISTER_WEBHOOK_URL, request, String.class);

        log.info("Register webhook result: " + result);
    }

}

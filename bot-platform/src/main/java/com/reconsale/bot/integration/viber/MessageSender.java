package com.reconsale.bot.integration.viber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reconsale.bot.model.viber.Sender;
import com.reconsale.bot.model.viber.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static com.reconsale.bot.constant.PropertiesNaming.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;

@Slf4j
@Component
public class MessageSender {

    private final static String URL = "https://chatapi.viber.com/pa/send_message";
    private final static String VIBER_AUTH_HEADER_NAME = "X-Viber-Auth-Token";

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    private String viberAuthToken;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendTextMessage(String text, String receiver) {
        sendTextMessage(buildTextMessage(text, receiver));
    }

    private void sendTextMessage(TextMessage textMessage) {
        String outboundPayload = null;
        try {
            outboundPayload = objectMapper.writeValueAsString(textMessage);
            log.info("Outbound payload: " + outboundPayload);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize! Value: " + textMessage);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(VIBER_AUTH_HEADER_NAME, viberAuthToken);

        HttpEntity<String> request = new HttpEntity<>(outboundPayload, headers);

        String result = restTemplate.postForObject(URL, request, String.class);

        log.info("Send result: " + result);

    }

    private TextMessage buildTextMessage(String text, String receiver) {
        TextMessage textMessage = new TextMessage();
        textMessage.setReceiver(receiver);
        textMessage.setMinApiVersion(1);
        textMessage.setSender(me());
        textMessage.setTrackingData("tracking data");
        textMessage.setType("text");
        textMessage.setText(text);
        return textMessage;
    }

    private Sender me() {
        Sender sender = new Sender();
        sender.setName("Bot");
        try {
            sender.setAvatar(new URI(""));
        } catch (URISyntaxException e) {
            // Ignore
        }
        return sender;

    }
}

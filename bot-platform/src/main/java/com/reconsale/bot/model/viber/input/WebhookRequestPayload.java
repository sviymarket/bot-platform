package com.reconsale.bot.model.viber.input;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class WebhookRequestPayload {

    private String event;
    private Date timestamp;

    @JsonProperty("chat_hostname")
    private String chatHostname;

    @JsonProperty("message_token")
    private String messageToken;

    private String type;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user")
    @JsonAlias("sender")
    private User user;

    private Message message;

    
    private Boolean silent;
    private Boolean subscribed;

    private String context;

}

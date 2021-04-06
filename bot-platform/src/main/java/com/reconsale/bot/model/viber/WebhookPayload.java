package com.reconsale.bot.model.viber;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reconsale.bot.model.viber.Message;
import com.reconsale.bot.model.viber.Sender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WebhookPayload {

    private String event;
    private Long timestamp;
    @JsonProperty("chat_hostname")
    private String chatHostname;
    @JsonProperty("message_token")
    private Long messageToken;
    private Sender sender;
    private Message message;
    private boolean silent;


}


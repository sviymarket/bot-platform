package com.reconsale.bot.model.viber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RegisterWebhookRequest {

    private String url;
    @JsonProperty("event_types")
    private List<String> eventTypes;

}

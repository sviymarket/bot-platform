package com.reconsale.bot.model.viber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {

    private String text;
    private String type;
    @JsonProperty("tracking_data")
    private String trackingData;
}

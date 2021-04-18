package com.reconsale.bot.model.viber.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reconsale.bot.model.viber.input.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TextMessage {

    private String receiver;
    @JsonProperty("min_api_version")
    private Integer minApiVersion;
    private User sender;
    @JsonProperty("tracking_data")
    private String trackingData;
    private String type;
    private String text;

}

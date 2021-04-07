package com.reconsale.bot.model.viber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;

@Getter
@Setter
@ToString
public class Sender {

    private String id;
    private String name;
    private URI avatar;
    private String language;
    private String country;
    @JsonProperty("api_version")
    private Integer apiVersion;
}

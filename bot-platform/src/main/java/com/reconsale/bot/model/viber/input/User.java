package com.reconsale.bot.model.viber.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

    private String id;
    private String name;
    private String avatar;
    private String language;
    private String country;
    @JsonProperty("api_version")
    private Integer apiVersion;

}

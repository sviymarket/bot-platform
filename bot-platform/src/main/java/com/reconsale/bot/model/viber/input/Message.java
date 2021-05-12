package com.reconsale.bot.model.viber.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String text;
    private String type;
    private String media;
    private Contact contact;

}

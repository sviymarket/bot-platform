package com.reconsale.bot.model.request;

import lombok.Data;

@Data
public class Payload {

    private String eventType;

    private String content;

    private String contentType;

    private String parameter;

    private String action;

    private String reference;
}

package com.reconsale.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload {

    private String content;

    private String contentType;

    private String parameter;

    private String reference;
}

package com.reconsale.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    private String id;

    private User user;

    private Context context;

    private Payload payload;

}

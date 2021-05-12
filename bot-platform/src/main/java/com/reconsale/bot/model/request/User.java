package com.reconsale.bot.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private String phoneNumber;

    public User(String id) {
        this.id = id;
    }

}

package com.reconsale.bot.model;

import com.reconsale.bot.model.request.Context;
import com.reconsale.bot.model.request.Payload;
import com.reconsale.bot.model.request.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    private String id;

    private User user;

    private Context context;

    private Payload payload;
    
    private String messageToken;

}

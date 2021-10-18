package com.reconsale.bot.engine;

import com.reconsale.bot.model.Request;

public interface SystemHandler {

    void handle(Request request);
    
    String getEvent();
}

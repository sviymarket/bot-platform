package com.reconsale.bot.engine;

import com.reconsale.bot.model.Response;

public interface ResponseCase<T> {

    T provideResponse(Response response);

    boolean evaluate(Response response);
}

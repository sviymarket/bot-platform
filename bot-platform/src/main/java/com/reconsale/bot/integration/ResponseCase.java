package com.reconsale.bot.integration;

import com.reconsale.bot.model.Response;

public interface ResponseCase<T> {

	T provideResponse(Response response);
	
	boolean evaluate(Response reponse);
}

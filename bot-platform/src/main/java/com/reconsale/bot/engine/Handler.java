package com.reconsale.bot.engine;

import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;

public interface Handler {

	Response handle(Request request);
	
	String mapping();
}

package com.reconsale.bot.test.handler;

import org.springframework.stereotype.Component;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;

@Component
public class DefaultHandler implements Handler {

	@Override
	public Response handle(Request request) {
		return Response.builder().text("Hello World").build();
	}

	@Override
	public String mapping() {
		return "*";
	}

}

package com.reconsale.bot.engine;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;

@Component
public class Dispatcher {

	@Autowired
	private Map<String, Handler> mappings;
		
	public Response dispatch(Request request) {
		return mappings.get(request.getPayload().getReference()).handle(request);
	}
	
}

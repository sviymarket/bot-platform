package com.reconsale.bot.engine;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.reconsale.bot.model.Context;

@Component
public class ContextManager {

	private Map<String, Context> contexts;
	
	public Context getContext(String user) {
		return contexts.get(user);
	}
}

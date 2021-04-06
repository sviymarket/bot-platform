package com.reconsale.bot.integration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.reconsale.bot.engine.ContextManager;
import com.reconsale.bot.engine.Dispatcher;

public abstract class Connector {
	
	@Autowired
	protected List<ResponseCase<?>> responseCases;
	
	@Autowired
	protected Dispatcher dispatcher;
	
	@Autowired
	protected ContextManager contextManager;

}

package com.reconsale.bot.integration;

import com.reconsale.bot.engine.ContextManager;
import com.reconsale.bot.engine.RequestDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class Connector {

    @Autowired
    protected List<ResponseCase<?>> responseCases;

    @Autowired
    protected RequestDispatcher requestDispatcher;

    @Autowired
    protected ContextManager contextManager;

}

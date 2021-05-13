package com.reconsale.bot.engine;

import com.reconsale.bot.model.request.Context;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContextManager {

    private Map<String, Context> contexts;

    public Context getContext(String user) {
        return contexts.get(user);
    }
}

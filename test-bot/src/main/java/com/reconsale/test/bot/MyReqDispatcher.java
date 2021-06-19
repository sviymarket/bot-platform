package com.reconsale.test.bot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.reconsale.bot.engine.RequestDispatcher;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;

@Component
@Primary
public class MyReqDispatcher extends RequestDispatcher {

    @Override
    protected String toKey(Request request) {
        String key = super.toKey(request);
        return !key.contains("|") ? key : key.substring(0, key.indexOf("|"));
    }
    
    @Override
    public Response dispatch(Request request) {
        Response response = super.dispatch(request);
        
        if (response == null) {
            response = Application.lastResponses.get(request.getUser().getId());
        } else {
            Application.lastResponses.put(request.getUser().getId(), response);
        }
        
        return response;
    }
}

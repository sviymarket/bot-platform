package com.reconsale.bot.integration.viber;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.reconsale.bot.integration.Connector;
import com.reconsale.bot.model.Context;
import com.reconsale.bot.model.Payload;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.User;
import com.reconsale.viber4j.ViberBot;
import com.reconsale.viber4j.ViberBotManager;
import com.reconsale.viber4j.incoming.Incoming;
import com.reconsale.viber4j.incoming.IncomingImpl;

public class ViberConnector extends Connector {
	

    private final String MESSAGE_EVENT = "message";
    private final String START_MSG_EVENT = "conversation_started";
	
    private Gson gson = new Gson();
    
    @Value("${}")
	private String botToken;
	
    @Autowired
	private ViberBotManager viberBotManager;	

    @RequestMapping(method = RequestMethod.POST, path = "/viberbot")
    public ResponseEntity<?> callback(@RequestBody String text) {
    	
    	ViberBot viberBot = viberBotManager.viberBot(botToken);
    	
    	Incoming incoming = IncomingImpl.fromString(text);
    	
    	String eventType = incoming.getEvent();    	
    	if (!StringUtils.equals(eventType, MESSAGE_EVENT) && !StringUtils.equals(incoming.getEvent(), START_MSG_EVENT)) {
            return ResponseEntity.ok().build();
    	}
    	
    	Request request = resolveRequest(text, incoming);
    	Response response = dispatcher.dispatch(request);
    	
    	resolveResponse(response);
    	
    	return ResponseEntity.ok().build();
    }

	private ResponseEntity<?> resolveResponse(Response response, ViberBot viberBot) {
		
	}

	private Request resolveRequest(String text, Incoming incoming) {
		Payload payload = gson.fromJson(incoming.getMessageText(), Payload.class);
		User user = new User(incoming.getSenderId());
		Context context = contextManager.getContext(user.getId());		
		return new Request(user, "1", context, payload);	
	}
	
	public void push(Response response, User user) {
		
	}

}

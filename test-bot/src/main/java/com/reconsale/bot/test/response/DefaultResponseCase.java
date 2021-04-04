package com.reconsale.bot.test.response;

import org.springframework.stereotype.Component;

import com.reconsale.bot.integration.viber.AbstractResponseCase;
import com.reconsale.bot.model.Response;
import com.reconsale.viber4j.ViberBot;

@Component
public class DefaultResponseCase extends AbstractResponseCase {
	
	@Override
	public Void provideResponse(Response response) {	    
    	ViberBot viberBot = viberBotManager.viberBot(botToken);
		String text = viberVisualizer.createMessage(response.getRefefence(), response.getText());
		viberBot.messageForUser(response.getUser()).postText(text);
		return null;
	}

	@Override
	public boolean evaluate(Response reponse) {
		return true;
	}

}

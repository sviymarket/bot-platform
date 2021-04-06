package com.reconsale.bot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.reconsale.viber4j.ViberBotManager;

@Component
public class ViberStarter {

	@Value("${viberAuthToken}")
	private String botToken;
	
    @Value("123")
    private String webHookUrl;
	
	private ViberBotManager viberBotManager = new ViberBotManager();
	
	@PostConstruct
	public void registerBot() {
		viberBotManager.viberBot(botToken).setWebHook(webHookUrl);
	}

}

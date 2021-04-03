package com.reconsale.bot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.reconsale.viber4j.ViberBotManager;

@Component
public class ViberStarter {

	@Value("${}")
	private String botToken;
	
    @Value("${viber.web-hook}")
    private String webHookUrl;
	
    @Autowired
	private ViberBotManager viberBotManager;	
	
	@PostConstruct
	public void registerBot() {
		viberBotManager.viberBot(botToken).setWebHook(webHookUrl);
	}

}

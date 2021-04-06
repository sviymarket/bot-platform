package com.reconsale.bot.integration.viber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;
import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.bot.integration.Visualizer;
import com.reconsale.viber4j.ViberBotManager;
import com.reconsale.viber4j.keyboard.RichMedia;
import com.reconsale.viber4j.keyboard.ViberKeyboard;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

	protected ViberBotManager viberBotManager = new ViberBotManager();
	
	@Autowired
	protected Visualizer<RichMedia, String, RichMedia, ViberKeyboard> viberVisualizer;
	
	protected Gson gson = new Gson();
    
    @Value("${viberAuthToken}")
    protected String botToken;
}

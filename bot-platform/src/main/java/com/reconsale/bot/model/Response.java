package com.reconsale.bot.model;

import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

	private String reference;
	
	private User user;
	
	private String text;
	
	private Carousel carousel;
	
	private Menu menu;
	
	private Tile tile;
}

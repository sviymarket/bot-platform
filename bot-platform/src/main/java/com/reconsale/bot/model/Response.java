package com.reconsale.bot.model;

import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;

import lombok.Data;

@Data
public class Response {

	private String refefence;
	
	private String user;
	
	private String text;
	
	private Carousel carousel;
	
	private Menu menu;
	
	private Tile tile;
}

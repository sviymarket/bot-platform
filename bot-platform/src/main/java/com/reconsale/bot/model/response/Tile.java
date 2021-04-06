package com.reconsale.bot.model.response;

import java.util.Map;

import lombok.Data;

@Data
public class Tile {
	private String text;	
	private String img;
	private Button button;
	
	private Map<String, String> labels;
	
}

package com.reconsale.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

	private User user;
	
	private String id;
	
	private Context context;
	
	private Payload payload;
	
}

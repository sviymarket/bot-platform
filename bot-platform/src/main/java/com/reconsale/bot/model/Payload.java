package com.reconsale.bot.model;

import lombok.Data;

@Data
public class Payload {

	private String content;

	private String contentType;

	private String parameter;
	
	private String action;
	
	private String reference;
}

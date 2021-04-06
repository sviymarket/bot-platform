package com.reconsale.bot.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Menu {
	
	private String template;
	
	private List<Button> buttons;

}

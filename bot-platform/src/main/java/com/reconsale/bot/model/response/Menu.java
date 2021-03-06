package com.reconsale.bot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Menu {
	
	private String template;
	
	private List<Button> buttons;

}

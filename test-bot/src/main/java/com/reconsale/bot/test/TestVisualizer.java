package com.reconsale.bot.test;

import com.reconsale.bot.integration.Visualizer;
import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import org.springframework.stereotype.Component;

@Component
public class TestVisualizer implements Visualizer<RichMedia, String, RichMedia, ViberKeyboard> {

	@Override
	public String createMessage(String reference, String message) {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public RichMedia createTile(String reference, Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViberKeyboard createMenu(String reference, Menu menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RichMedia createCarousel(String reference, Carousel carousel) {
		// TODO Auto-generated method stub
		return null;
	}

}

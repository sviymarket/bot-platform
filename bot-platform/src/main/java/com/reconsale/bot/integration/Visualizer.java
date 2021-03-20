package com.reconsale.bot.integration;

import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;

public interface Visualizer<B, C, M> {

	B createButton(Button button);
	
	M createMenu(Menu menu);
	
	C createCarousel(Carousel carousel);	
}

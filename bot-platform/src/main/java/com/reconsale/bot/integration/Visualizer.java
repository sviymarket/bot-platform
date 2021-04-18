package com.reconsale.bot.integration;

import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;

public interface Visualizer<T, B, C, M> {

    B createMessage(String reference, String message);

    T createTile(String reference, Tile tile);

    M createMenu(String reference, Menu menu);

    C createCarousel(String reference, Carousel carousel);
}

package com.reconsale.bot.model;

import com.reconsale.bot.model.request.User;
import com.reconsale.bot.model.response.Carousel;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.viber.output.RichMedia;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Response {

    private String reference;

    private User user;

    private String text;

    private Menu menu;

    private RichMedia richMedia;

    private Carousel carousel;
    
    private List<Tile> tiles;
}

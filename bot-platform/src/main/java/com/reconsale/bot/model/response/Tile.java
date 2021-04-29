package com.reconsale.bot.model.response;

import com.reconsale.bot.model.response.styling.TileStyle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {

    private String contentImage;
    private String text;
    private Button button;
    private String bottomText;

    // TODO: decide if we want specific url for image, text and button
    private String tileUrl;

    private TileStyle tileStyle;

}
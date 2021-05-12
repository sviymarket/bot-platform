package com.reconsale.bot.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {

    private String contentImage; // 6 3
    private String text; // 6 2

    private Button button; // 6 1

    private String bottomText; // 6 1

}
package com.reconsale.bot.model.response.styling;

import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TileStyle {

    private String backgroundColor;
    private ViberButton.TextSize textSize;
    private String textColor;

    private int imageHeight;
    private int textHeight;
    private int buttonHeight;
    private int bottomTextHeight;

}

package com.reconsale.bot.model.response.styling;

import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ButtonStyle {

    private String backgroundColor;
    private ViberButton.TextSize textSize;
    private String textColor;
    private int width; //[1..6]

}

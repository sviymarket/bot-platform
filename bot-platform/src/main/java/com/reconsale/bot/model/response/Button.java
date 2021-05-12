package com.reconsale.bot.model.response;

import com.reconsale.bot.model.response.styling.ButtonStyle;
import lombok.Getter;
import lombok.Setter;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;

@Getter
@Setter
public class Button {

    private String id;
    private ButtonAction buttonAction;
    private ButtonStyle buttonStyle;

    public Button(String id, ButtonStyle buttonStyle) {
        this.id = id;
        this.buttonAction = new ButtonAction(PRESSED + ":" + id);
        this.buttonStyle = buttonStyle;
    }

    public Button(String id, ButtonAction buttonAction, ButtonStyle buttonStyle) {
        this.id = id;
        this.buttonAction = buttonAction;
        this.buttonStyle = buttonStyle;
    }

}


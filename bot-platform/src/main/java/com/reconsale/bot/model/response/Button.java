package com.reconsale.bot.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Button {

    private String id;
    private String label;
    private ButtonAction buttonAction;
    private List<Button> buttons;

    public Button(String id, String label, ButtonAction buttonAction) {
        this.id = id;
        this.label = label;
        this.buttonAction = buttonAction;
    }

    public Button(String id, String label, ButtonAction buttonAction, List<Button> buttons) {
        this.id = id;
        this.label = label;
        this.buttonAction = buttonAction;
        this.buttons = buttons;
    }

}

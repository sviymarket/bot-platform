package com.reconsale.bot.model.response;

import com.google.gson.Gson;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Button {

    private String id;
    private String action;
    private ButtonActionType buttonActionType;
    private ButtonStyle buttonStyle;
    private String backgroundImage;
    private Integer buttonHeight;
    private Integer buttonWidth;
    private String text;
    private boolean boldText;

    // TODO: find a better way
    public static Button navigationButton(String id, ButtonStyle buttonStyle) {
        Button button = new Button();
        button.setId(id);
        button.setAction(new Gson().toJson(new ButtonAction(PRESSED + ":" + id)));
        button.setButtonActionType(ButtonActionType.REPLY);
        button.setButtonStyle(buttonStyle);
        return button;
    }

    public Button(String id, String action, ButtonActionType buttonActionType, ButtonStyle buttonStyle) {
        this.id = id;
        this.action = action;
        this.buttonActionType = buttonActionType;
        this.buttonStyle = buttonStyle;
    }

}


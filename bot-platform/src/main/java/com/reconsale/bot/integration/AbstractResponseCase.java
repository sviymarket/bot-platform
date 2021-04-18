package com.reconsale.bot.integration;

import com.google.gson.Gson;
import com.reconsale.bot.engine.ResponseCase;
import com.reconsale.bot.integration.viber.ViberBotManager;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.response.styling.MenuStyle;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.reconsale.bot.constant.Properties.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;
import static com.reconsale.bot.model.constant.Buttons.DEFAULT_BUTTON_WIDTH;
import static com.reconsale.bot.model.constant.Colors.*;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

    @Autowired
    protected ViberBotManager viberBotManager;

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    protected String botToken;

    private Gson gson = new Gson();

    private MenuStyle defaultMenuStyle = new MenuStyle(true, GREY);
    private ButtonStyle defaultButtonStyle = new ButtonStyle(RED, ViberButton.TextSize.MEDIUM, WHITE, DEFAULT_BUTTON_WIDTH);

    protected ViberKeyboard fromMenu(Menu menu) {
        if (Objects.isNull(menu) || CollectionUtils.isEmpty(menu.getButtons())) {
            return null;
        }

        ResourceBundle resourceBundle = getResourceBundle();

        List<Button> buttons = menu.getButtons();
        List<ViberButton> viberButtons;
        if (CollectionUtils.isEmpty(buttons)) {
            viberButtons = Collections.emptyList();
        } else {
            viberButtons = buttons.stream()
                    .map(b -> fromButton(b, resourceBundle))
                    .collect(Collectors.toList());
        }

        MenuStyle menuStyle = menu.getMenuStyle();
        if (Objects.isNull(menuStyle)) {
            menuStyle = defaultMenuStyle;
        }

        ViberKeyboard viberKeyboard = new ViberKeyboard();
        viberKeyboard.setDefaultHeight(!menuStyle.isMinimalHeight());
        viberKeyboard.setBgColor(menuStyle.getBackgroundColor());
        //viberKeyboard.setButtonsGroupColumns(2);
        //viberKeyboard.setButtonsGroupRows(3);
        viberButtons.forEach(vb ->
                viberKeyboard.addButton(vb));
        return viberKeyboard;
    }

    protected ViberButton fromButton(Button button, ResourceBundle resourceBundle) {
        ButtonStyle buttonStyle = button.getButtonStyle();
        if (Objects.isNull(buttonStyle)) {
            buttonStyle = defaultButtonStyle;
        }
        ViberButton viberButton = new ViberButton(gson.toJson(button.getButtonAction()));
        viberButton.setActionType(BtnActionType.REPLY);
        viberButton.setBgColor(buttonStyle.getBackgroundColor());
        viberButton.setTextSize(buttonStyle.getTextSize());

        String localizedLabel = resourceBundle.getString(button.getId());
        viberButton.setText(buildText(localizedLabel, buttonStyle.getTextColor()));
        viberButton.setColumns(buttonStyle.getWidth());
        viberButton.setRows(1);

        return viberButton;
    }

    private String buildText(String label, String color) {
        return "<font color='" + color + "'>" + label + "</font>";
    }

    protected ResourceBundle getResourceBundle() {
        // TODO: build UA locale
        return ResourceBundle.getBundle("messages", Locale.FRANCE);
    }

}

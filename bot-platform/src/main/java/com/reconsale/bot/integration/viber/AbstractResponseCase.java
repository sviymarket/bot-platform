package com.reconsale.bot.integration.viber;

import com.google.gson.Gson;
import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.bot.integration.Visualizer;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.viber4j.ViberBotManager;
import com.reconsale.viber4j.keyboard.BtnActionType;
import com.reconsale.viber4j.keyboard.RichMedia;
import com.reconsale.viber4j.keyboard.ViberButton;
import com.reconsale.viber4j.keyboard.ViberKeyboard;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.stream.Collectors;

import static com.reconsale.bot.constant.PropertiesNaming.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

    @Autowired
    protected ViberBotManager viberBotManager;

    @Autowired
    protected Visualizer<RichMedia, String, RichMedia, ViberKeyboard> viberVisualizer;

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    protected String botToken;

    private Gson gson = new Gson();

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
                    .map(b -> fromButton(b, buttons.size(), resourceBundle))
                    .collect(Collectors.toList());
        }

        ViberKeyboard viberKeyboard = new ViberKeyboard();
        viberKeyboard.setDefaultHeight(false);
        // TODO: define constants for colors
        viberKeyboard.setBgColor("#eeeeee");
        //viberKeyboard.setButtonsGroupColumns(2);
        //viberKeyboard.setButtonsGroupRows(3);
        viberButtons.forEach(vb ->
                viberKeyboard.addButton(vb));
        return viberKeyboard;
    }

    // TODO: pass styling as input parameter
    protected ViberButton fromButton(Button button, int totalButtons, ResourceBundle resourceBundle) {

        ViberButton viberButton = new ViberButton(gson.toJson(button.getButtonAction()));
        viberButton.setActionType(BtnActionType.REPLY);
        viberButton.setBgColor("#e6132f");
        viberButton.setTextSize(ViberButton.TextSize.MEDIUM);

        String localizedLabel = resourceBundle.getString(button.getId());
        viberButton.setText("<font color='#ffffff'>" + localizedLabel + "</font>");
        viberButton.setColumns(totalButtons > 1 ? 3 : 6);
        viberButton.setRows(1);

        return viberButton;
    }

    protected ResourceBundle getResourceBundle() {
        // TODO: build UA locale
        return ResourceBundle.getBundle("messages", Locale.FRANCE);
    }

}

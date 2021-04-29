package com.reconsale.bot.integration;

import com.google.gson.Gson;
import com.reconsale.bot.engine.ResponseCase;
import com.reconsale.bot.integration.viber.ViberBotManager;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.response.styling.MenuStyle;
import com.reconsale.bot.model.response.styling.TileStyle;
import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.reconsale.bot.constant.Properties.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;
import static com.reconsale.bot.model.constant.Buttons.DEFAULT_BUTTON_WIDTH;
import static com.reconsale.bot.model.constant.Buttons.DEFAULT_TILE_HEIGHT;
import static com.reconsale.bot.model.constant.Colors.*;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

    @Autowired
    protected ViberBotManager viberBotManager;

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    protected String botToken;

    private Gson gson = new Gson();

    private MenuStyle defaultMenuStyle = new MenuStyle(true, GREY);
    private ButtonStyle defaultButtonStyle = new ButtonStyle(RED, ViberButton.TextSize.MEDIUM, WHITE, DEFAULT_BUTTON_WIDTH);
    private TileStyle defaulttileStyle = new TileStyle(RED, ViberButton.TextSize.MEDIUM, WHITE, 5, 0, 2, 0);

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

    protected RichMedia fromTiles(List<Tile> tiles) {
        if (CollectionUtils.isEmpty(tiles)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(RED);
        richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR); // ?

        ResourceBundle resourceBundle = getResourceBundle();

        for (Tile tile : tiles) {
            TileStyle tileStyle = tile.getTileStyle();
            if (Objects.isNull(tileStyle)) {
                tileStyle = defaulttileStyle;
            }

            String tileUrl = tile.getTileUrl();
            ViberButton imageButton = new ViberButton(tileUrl);
            if (Objects.nonNull(tileUrl)) {
                imageButton.setActionType(BtnActionType.OPEN_URL);
            }
            imageButton.setBgColor(tileStyle.getBackgroundColor());
            imageButton.setImage(tile.getContentImage());
            imageButton.setColumns(DEFAULT_BUTTON_WIDTH);
            imageButton.setRows(tileStyle.getImageHeight());


            ViberButton button = fromButton(tile.getButton(), getResourceBundle());
            if (Objects.nonNull(tileUrl)) {
                button = new ViberButton(tileUrl);
                button.setActionType(BtnActionType.OPEN_URL);
                String localizedLabel = resourceBundle.getString(tile.getButton().getId());
                button.setText(buildText(localizedLabel, tileStyle.getTextColor()));

            }
            button.setBgColor(tileStyle.getBackgroundColor());
            button.setColumns(DEFAULT_BUTTON_WIDTH);
            button.setRows(tileStyle.getButtonHeight());
            button.setTextSize(tileStyle.getTextSize());
            button.setTextHAlign(ViberButton.TextAlign.MIDDLE);

            richMedia.addButton(imageButton);
            richMedia.addButton(button);
        }

        return richMedia;
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
        Locale locale = new Locale("ukr", "UA");
        return ResourceBundle.getBundle("messages", locale);
    }

}

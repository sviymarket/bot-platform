package com.reconsale.bot.integration;

import com.google.gson.Gson;
import com.reconsale.bot.conf.UTF8Control;
import com.reconsale.bot.engine.ResponseCase;
import com.reconsale.bot.integration.viber.ViberBotManager;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.*;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.response.styling.MenuStyle;
import com.reconsale.bot.model.response.styling.TileStyle;
import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.reconsale.bot.constant.HandlerKeys.KEY;
import static com.reconsale.bot.constant.Properties.VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE;
import static com.reconsale.bot.model.constant.Buttons.*;
import static com.reconsale.bot.model.constant.Colors.*;

public abstract class AbstractResponseCase implements ResponseCase<Object> {

    @Autowired
    protected ViberBotManager viberBotManager;

    @Value(VIBER_BOT_AUTHENTICATION_TOKEN_PROPERTY_REFERENCE)
    protected String botToken;

    private MenuStyle defaultMenuStyle = new MenuStyle(true, null);
    private ButtonStyle defaultButtonStyle = new ButtonStyle(RED, ViberButton.TextSize.MEDIUM, WHITE, DEFAULT_BUTTON_WIDTH);
    private TileStyle defaultTileStyle = new TileStyle(RED, ViberButton.TextSize.MEDIUM, WHITE, 7, 0, 0, 0);

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

        // Disallow user input

        viberKeyboard.setInputFieldState(ButtonContainer.InputFieldState.HIDDEN);
        //viberKeyboard.setBgColor(null);
        return viberKeyboard;
    }

    protected RichMedia fromTiles(List<Tile> tiles) {
        if (CollectionUtils.isEmpty(tiles)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(RED);
        //richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        //richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR); // ?

        ResourceBundle resourceBundle = getResourceBundle();

        for (Tile tile : tiles) {
            TileStyle tileStyle = tile.getTileStyle();
            if (Objects.isNull(tileStyle)) {
                tileStyle = defaultTileStyle;
            }

            String tileUrl = tile.getTileUrl();
            ViberButton imageButton = new ViberButton(tileUrl);
            imageButton.setActionType(BtnActionType.OPEN_URL);
            imageButton.setBgColor(tileStyle.getBackgroundColor());
            imageButton.setImage(tile.getContentImage());
            imageButton.setColumns(DEFAULT_BUTTON_WIDTH);
            imageButton.setRows(tileStyle.getImageHeight());
            imageButton.setSilent(true);

            richMedia.addButton(imageButton);


            if (tileStyle.getButtonHeight() > 0) {
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
                button.setSilent(true);
                button.setBgMediaType(ViberButton.BgMediaType.PICTURE);
                richMedia.addButton(button);
            }
        }

        richMedia.setDefaultHeight(null);
        return richMedia;
    }

    protected RichMedia fromButtonTiles(List<ButtonTile> buttonTiles) {
        if (CollectionUtils.isEmpty(buttonTiles)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        //richMedia.setBgColor(WHITE);
        //richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        //richMedia.setInputFieldState(ButtonContainer.InputFieldState.HIDDEN); // ?

        for (ButtonTile buttonTile : buttonTiles) {
            for (Button button : buttonTile.getButtons()) {
                ViberButton imageButton = new ViberButton("");
                imageButton.setActionType(BtnActionType.NONE);
                imageButton.setImage(button.getBackgroundImage());
                //imageButton.setBgMedia(button.getBackgroundImage());
                imageButton.setRows(button.getButtonHeight());
                imageButton.setColumns(DEFAULT_BUTTON_WIDTH);
                imageButton.setBgColor(RED);
                if (Objects.nonNull(button.getButtonWidth())) {
                    imageButton.setColumns(button.getButtonWidth());
                }

                ButtonStyle buttonStyle = button.getButtonStyle();
                if (Objects.nonNull(buttonStyle)) {
                    imageButton.setBgColor(buttonStyle.getBackgroundColor());
                    if (button.isBoldText()) {
                        imageButton.setText(buildBoldText(button.getText(), buttonStyle.getTextColor()));
                    } else {
                        imageButton.setText(buildText(button.getText(), buttonStyle.getTextColor()));
                    }
                    imageButton.setTextSize(buttonStyle.getTextSize());

                }

                imageButton.setTextHAlign(ViberButton.TextAlign.MIDDLE);
                imageButton.setTextVAlign(ViberButton.TextAlign.MIDDLE);

                imageButton.setBgMediaType(ViberButton.BgMediaType.PICTURE);

                richMedia.addButton(imageButton);
            }
        }

        richMedia.setDefaultHeight(null);
        richMedia.setBgColor(RED);

        return richMedia;
    }

    protected ViberButton fromButton(Button button, ResourceBundle resourceBundle) {
        ButtonStyle buttonStyle = button.getButtonStyle();
        if (Objects.isNull(buttonStyle)) {
            buttonStyle = defaultButtonStyle;
        }

        ViberButton viberButton = new ViberButton(button.getAction());
        viberButton.setActionType(getActionType(button));

        viberButton.setBgColor(buttonStyle.getBackgroundColor());
        viberButton.setTextSize(buttonStyle.getTextSize());

        String localizedLabel = resourceBundle.getString(button.getId());
        viberButton.setText(buildText(localizedLabel, buttonStyle.getTextColor()));
        viberButton.setColumns(buttonStyle.getWidth());
        viberButton.setRows(1);

        viberButton.setSilent(true);

        if (StringUtils.isNotBlank(button.getBackgroundImage())) {
            viberButton.setImage(button.getBackgroundImage());
            viberButton.setBgMedia(button.getBackgroundImage());
            viberButton.setBgMediaType(ViberButton.BgMediaType.PICTURE);
            viberButton.setText("");
        }

        //viberButton.setImage("https://img.icons8.com/carbon-copy/2x/buy--v1.png");
        //viberButton.setBgMediaType(ViberButton.BgMediaType.PICTURE);

        return viberButton;
    }

    private BtnActionType getActionType(Button button) {
        BtnActionType resolvedActionType = BtnActionType.NONE;

        if (Objects.isNull(button) || Objects.isNull(button.getButtonActionType())) {
            return resolvedActionType;
        }

        ButtonActionType buttonActionType = button.getButtonActionType();
        if (ButtonActionType.SHARE_PHONE == buttonActionType) {
            resolvedActionType = BtnActionType.SHARE_PHONE;
        } else if (ButtonActionType.OPEN_URL == buttonActionType) {
            resolvedActionType = BtnActionType.OPEN_URL;
        } else if (ButtonActionType.REPLY == buttonActionType) {
            resolvedActionType = BtnActionType.REPLY;
        }
        return resolvedActionType;
    }

    protected RichMedia fromTextTileRows(List<TextTileRow> textTileRows) {
        if (CollectionUtils.isEmpty(textTileRows)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(WHITE);
        //richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        //richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR); // ?

        int partitionSize = TEXT_TILE_PARTITION;
        List<List<TextTileRow>> partitions = new LinkedList<List<TextTileRow>>();
        for (int i = 0; i < textTileRows.size(); i += partitionSize) {
            partitions.add(textTileRows.subList(i,
                    Math.min(i + partitionSize, textTileRows.size())));
        }


        for (List<TextTileRow> partition : partitions) {
            int freeSpace = 7;
            // Header
            ViberButton itemHeader = new ViberButton("");
            itemHeader.setActionType(BtnActionType.NONE);
            itemHeader.setBgColor(RED);
            itemHeader.setText(buildText("Товар", WHITE));
            itemHeader.setColumns(TEXT_TILE_ITEM_HEADER_WIDTH);
            itemHeader.setRows(1);

            ViberButton priceHeader = new ViberButton("");
            priceHeader.setActionType(BtnActionType.NONE);
            priceHeader.setBgColor(RED);
            priceHeader.setText(buildText("Грн", WHITE));
            priceHeader.setColumns(TEXT_TILE_PRICE_HEADER_WIDTH);
            priceHeader.setRows(1);

            richMedia.addButton(itemHeader);
            richMedia.addButton(priceHeader);
            freeSpace--;


            for (TextTileRow textTileRow : partition) {
                ViberButton ib = new ViberButton("");
                ib.setActionType(BtnActionType.NONE);
                ib.setBgColor(GREY);
                ib.setText(textTileRow.getItem());
                ib.setColumns(TEXT_TILE_ITEM_HEADER_WIDTH);
                ib.setRows(1);

                ViberButton pb = new ViberButton("");
                pb.setActionType(BtnActionType.NONE);
                pb.setBgColor(GREY);
                pb.setText("" + textTileRow.getPrice());
                pb.setColumns(TEXT_TILE_PRICE_HEADER_WIDTH);
                pb.setRows(1);

                richMedia.addButton(ib);
                richMedia.addButton(pb);
                freeSpace--;
            }

            if (freeSpace > 0) {
                for (int i = 0; i < freeSpace; i++) {
                    ViberButton ib = new ViberButton("");
                    ib.setActionType(BtnActionType.NONE);
                    ib.setBgColor(GREY);
                    ib.setColumns(TEXT_TILE_ITEM_HEADER_WIDTH);
                    ib.setRows(1);

                    ViberButton pb = new ViberButton("");
                    pb.setActionType(BtnActionType.NONE);
                    pb.setBgColor(GREY);
                    pb.setColumns(TEXT_TILE_PRICE_HEADER_WIDTH);
                    pb.setRows(1);

                    richMedia.addButton(ib);
                    richMedia.addButton(pb);

                }
            }


        }

        richMedia.setDefaultHeight(null);
        return richMedia;
    }

    protected RichMedia fromListOfDataMap(String handlerId, List<List<Map<String, String>>> listOfListOfMap) {
        if (CollectionUtils.isEmpty(listOfListOfMap)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(WHITE);
        //richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        //richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR); // ?

        for (List<Map<String, String>> listOfMap : listOfListOfMap) {

            for (Map<String, String> m : listOfMap) {
                String ca = "";
                int freeSpace = DEFAULT_TILE_HEIGHT;
                if (m.containsKey(KEY)) {
                    String key = m.get(KEY);
                    ca = new Gson().toJson(new ButtonAction(PRESSED + ":" + handlerId + "?" + KEY + "=" + key));

                    // Header
                    ViberButton itemHeader = new ViberButton(ca);
                    itemHeader.setActionType(BtnActionType.REPLY);
                    itemHeader.setText(buildText(key, WHITE));
                    itemHeader.setBgColor(RED);
                    itemHeader.setColumns(DEFAULT_BUTTON_WIDTH);
                    itemHeader.setRows(1);

                    richMedia.addButton(itemHeader);
                    freeSpace--;
                }

                for (Map.Entry<String, String> pair : m.entrySet()) {
                    if (freeSpace == 0) {
                        break;
                    }
                    if (StringUtils.equals(KEY, pair.getKey())) {
                        continue;
                    }
                    ViberButton kb = new ViberButton(ca);
                    kb.setActionType(BtnActionType.REPLY);
                    kb.setBgColor(GREY);
                    kb.setText(pair.getKey());
                    kb.setColumns(3);
                    kb.setRows(1);

                    ViberButton vb = new ViberButton(ca);
                    vb.setActionType(BtnActionType.REPLY);
                    vb.setBgColor(GREY);
                    vb.setText(pair.getValue());
                    vb.setColumns(3);
                    vb.setRows(1);

                    richMedia.addButton(kb);
                    richMedia.addButton(vb);

                    freeSpace--;
                }

                for (int i = 0; i < freeSpace; i++) {
                    ViberButton kb = new ViberButton("");
                    kb.setActionType(BtnActionType.NONE);
                    kb.setBgColor(GREY);
                    kb.setColumns(3);
                    kb.setRows(1);

                    ViberButton vb = new ViberButton(ca);
                    vb.setActionType(BtnActionType.NONE);
                    vb.setBgColor(GREY);
                    vb.setColumns(3);
                    vb.setRows(1);

                    richMedia.addButton(kb);
                    richMedia.addButton(vb);
                }
            }
        }

        richMedia.setDefaultHeight(null);
        return richMedia;

    }

    protected RichMedia fromDataMap(String handlerId, List<Map<String, String>> listOfMap) {
        if (CollectionUtils.isEmpty(listOfMap)) {
            return null;
        }

        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(WHITE);
        //richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6); // ?
        richMedia.setButtonsGroupRows(DEFAULT_TILE_HEIGHT);
        //richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR); // ?

        int partitionSize = TEXT_TILE_PARTITION;
        List<List<Map<String, String>>> partitions = new LinkedList<>();
        for (int i = 0; i < listOfMap.size(); i += partitionSize) {
            partitions.add(listOfMap.subList(i,
                    Math.min(i + partitionSize, listOfMap.size())));
        }


        for (Map<String, String> m : listOfMap) {
            String ca = "";
            int freeSpace = DEFAULT_TILE_HEIGHT - 1;
            if (m.containsKey(KEY)) {
                String key = m.get(KEY);

                // Header
                ViberButton itemHeader = new ViberButton(ca);
                itemHeader.setActionType(BtnActionType.NONE);
                itemHeader.setText(buildText(key, WHITE));
                itemHeader.setBgColor(RED);
                itemHeader.setColumns(DEFAULT_BUTTON_WIDTH);
                itemHeader.setRows(1);

                richMedia.addButton(itemHeader);
                freeSpace--;
            }

            for (Map.Entry<String, String> pair : m.entrySet()) {
                if (freeSpace == 0) {
                    break;
                }
                if (StringUtils.equals(KEY, pair.getKey())) {
                    continue;
                }
                ViberButton kb = new ViberButton(ca);
                kb.setActionType(BtnActionType.NONE);
                kb.setBgColor(GREY);
                kb.setText(pair.getKey());
                kb.setColumns(3);
                kb.setRows(1);

                ViberButton vb = new ViberButton(ca);
                vb.setActionType(BtnActionType.NONE);
                vb.setBgColor(GREY);
                vb.setText(pair.getValue());
                vb.setColumns(3);
                vb.setRows(1);

                richMedia.addButton(kb);
                richMedia.addButton(vb);

                freeSpace--;
            }

            for (int i = 0; i < freeSpace; i++) {
                ViberButton kb = new ViberButton("");
                kb.setActionType(BtnActionType.NONE);
                kb.setBgColor(GREY);
                kb.setColumns(3);
                kb.setRows(1);

                ViberButton vb = new ViberButton(ca);
                vb.setActionType(BtnActionType.NONE);
                vb.setBgColor(GREY);
                vb.setColumns(3);
                vb.setRows(1);

                richMedia.addButton(kb);
                richMedia.addButton(vb);
            }

            String key = m.get(KEY);
            ca = new Gson().toJson(new ButtonAction(PRESSED + ":" + handlerId + "?" + KEY + "=" + key));

            // Header
            ViberButton itemHeader = new ViberButton(ca);
            itemHeader.setActionType(BtnActionType.REPLY);
            itemHeader.setText(buildText("Детальніше", WHITE));
            itemHeader.setSilent(true);
            itemHeader.setBgColor(RED);
            itemHeader.setColumns(DEFAULT_BUTTON_WIDTH);
            itemHeader.setRows(1);

            richMedia.addButton(itemHeader);
            freeSpace--;
        }


        richMedia.setDefaultHeight(null);
        return richMedia;
    }

    private String buildText(String label, String color) {
        return "<font color='" + color + "'>" + label + "</font>";
    }

    private String buildBoldText(String label, String color) {
        return "<b><font color='" + color + "'>" + label + "</font></b>";
    }

    protected ResourceBundle getResourceBundle() {
        // TODO: build UA locale
        Locale locale = new Locale("uk", "UA");
        return ResourceBundle.getBundle("messages", locale, new UTF8Control());
    }

    protected ButtonTile textWithImage(String text, String imageUrl) {
        ButtonTile buttonTile = new ButtonTile();
        List<Button> buttons = new ArrayList<>();
        buttonTile.setButtons(buttons);

        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.setBackgroundColor(Colors.LIGHT_GREY);

        Button top = new Button();
        //top.setBackgroundImage(imageUrl);
        top.setButtonHeight(4);
        top.setText(text);
        top.setButtonStyle(buttonStyle);
        buttons.add(top);

        Button bottom = new Button();
        bottom.setBackgroundImage(imageUrl);
        bottom.setButtonHeight(3);
        bottom.setButtonStyle(buttonStyle);
        buttons.add(bottom);

        buttonTile.setButtons(buttons);
        return buttonTile;
    }

    protected RichMedia fromTextWithImage(ButtonTile buttonTile) {
        RichMedia richMedia = new RichMedia();
        richMedia.setButtonsGroupColumns(6);
        richMedia.setButtonsGroupRows(7);
        richMedia.setDefaultHeight(null);
        richMedia.setBgColor(Colors.LIGHT_GREY);

        List<Button> buttons = buttonTile.getButtons();
        Iterator it = buttons.iterator();

        while (it.hasNext()) {
            Button button = (Button) it.next();

            ViberButton viberButton = new ViberButton("");
            viberButton.setActionType(BtnActionType.NONE);

            viberButton.setRows(button.getButtonHeight());
            viberButton.setColumns(6);

            viberButton.setBgColor(Colors.LIGHT_GREY);

            if (Objects.nonNull(button.getBackgroundImage())) {
                viberButton.setImage(button.getBackgroundImage());
                viberButton.setBgMediaType(ViberButton.BgMediaType.PICTURE);
            }

            if (Objects.nonNull(button.getButtonWidth())) {
                viberButton.setColumns(button.getButtonWidth());
            }

            if (StringUtils.isNotBlank(button.getText())) {
                viberButton.setText(button.getText());
            }

            ButtonStyle buttonStyle = button.getButtonStyle();
            if (Objects.nonNull(buttonStyle)) {
                viberButton.setBgColor(buttonStyle.getBackgroundColor());
                viberButton.setTextSize(buttonStyle.getTextSize());
            }

            viberButton.setTextHAlign(ViberButton.TextAlign.LEFT);
            viberButton.setTextVAlign(ViberButton.TextAlign.MIDDLE);

            richMedia.addButton(viberButton);
        }

        return richMedia;
    }

}

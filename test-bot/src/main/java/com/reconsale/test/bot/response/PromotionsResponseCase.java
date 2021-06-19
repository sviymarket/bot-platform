package com.reconsale.test.bot.response;

import com.google.gson.Gson;
import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.integration.viber.ViberBotManager;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonAction;
import com.reconsale.bot.model.response.ButtonActionType;
import com.reconsale.bot.model.response.Tile;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.response.styling.TileStyle;
import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import com.reconsale.test.bot.constant.GlobalColors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.reconsale.bot.model.constant.Colors.RED;
import static com.reconsale.test.bot.constant.MenuItems.PERSONAL_PROPOSALS;

@Slf4j
@Component
public class PromotionsResponseCase extends AbstractResponseCase {

    private TileStyle defaultTileStyle = new TileStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, GlobalColors.BLACK, 5, 1, 1, 0);
    private ButtonStyle defaultButtonStyle = new ButtonStyle(RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);

    @Override
    public Object provideResponse(Response response) {
        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = ViberBotManager.viberBot(botToken);
            List<Tile> tiles = response.getTiles();
            if (Objects.nonNull(tiles)) {
                viberBot.messageForUser(response.getUser().getId())
                        .postCarousel(fromTiles1(tiles));
                viberBot.messageForUser(response.getUser().getId())
                        .postText("Персональна пропозиція!");
            }
            ViberKeyboard viberKeyboard = fromMenu(response.getMenu());

            viberBot.messageForUser(response.getUser().getId())
                    .postKeyboard(viberKeyboard);
        } else {
            log.info("Don't have user id to send anything...");
        }
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && PERSONAL_PROPOSALS.equals(response.getReference());
    }

    protected ViberButton fromButton(Button button, String text) {
        ButtonStyle buttonStyle = button.getButtonStyle();
        if (Objects.isNull(buttonStyle)) {
            buttonStyle = defaultButtonStyle;
        }

        ViberButton viberButton = new ViberButton(button.getAction());
        viberButton.setActionType(getViberActionType(button));
        viberButton.setBgColor(buttonStyle.getBackgroundColor());
        viberButton.setTextSize(buttonStyle.getTextSize());

        viberButton.setText(this.buildText(text, GlobalColors.BLACK));
        viberButton.setColumns(buttonStyle.getWidth());
        viberButton.setRows(1);
        viberButton.setSilent(true);
        return viberButton;
    }

    private BtnActionType getViberActionType(Button button) {
        BtnActionType resolvedActionType = BtnActionType.NONE;
        if (!Objects.isNull(button) && !Objects.isNull(button.getButtonActionType())) {
            ButtonActionType buttonActionType = button.getButtonActionType();
            if (ButtonActionType.SHARE_PHONE == buttonActionType) {
                resolvedActionType = BtnActionType.SHARE_PHONE;
            } else if (ButtonActionType.OPEN_URL == buttonActionType) {
                resolvedActionType = BtnActionType.OPEN_URL;
            } else if (ButtonActionType.REPLY == buttonActionType) {
                resolvedActionType = BtnActionType.REPLY;
            }

            return resolvedActionType;
        } else {
            return resolvedActionType;
        }
    }

    private RichMedia fromTiles1(List<Tile> tiles) {
        if (CollectionUtils.isEmpty(tiles)) {
            return null;
        } else {
            RichMedia richMedia = new RichMedia();
            richMedia.setBgColor(Colors.WHITE);
            richMedia.setDefaultHeight(true);
            richMedia.setButtonsGroupColumns(6);
            richMedia.setButtonsGroupRows(7);
            richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR);
            Iterator var4 = tiles.iterator();

            while (var4.hasNext()) {
                Tile tile = (Tile) var4.next();
                TileStyle tileStyle = tile.getTileStyle();
                if (Objects.isNull(tileStyle)) {
                    tileStyle = defaultTileStyle;
                }

                String tileUrl = tile.getTileUrl();
                ViberButton imageButton = new ViberButton("");
                imageButton.setActionType(BtnActionType.NONE);
                imageButton.setBgColor(Colors.WHITE);
                imageButton.setImage(tile.getContentImage());
                imageButton.setColumns(6);
                imageButton.setRows(tileStyle.getImageHeight());
                ViberButton button = fromButton(tile.getButton(), tile.getText());
                if (Objects.nonNull(tileUrl)) {
                    button = new ViberButton(tileUrl);
                    button.setActionType(BtnActionType.OPEN_URL);
                    button.setText(this.buildText(tile.getText(), GlobalColors.BLACK));
                }
                button.setBgColor(Colors.WHITE);
                button.setColumns(6);
                button.setRows(tileStyle.getButtonHeight());
                button.setTextSize(tileStyle.getTextSize());
                button.setTextHAlign(ViberButton.TextAlign.MIDDLE);
                button.setSilent(true);

                richMedia.addButton(imageButton);
                richMedia.addButton(button);
                String productId = tile.getBottomText();
                richMedia.addButton(addToCartViberButton(productId, tileStyle));
            }
            return richMedia;
        }
    }

    private ViberButton addToCartViberButton(String productId, TileStyle tileStyle) {
        log.info("Add to cart:" + productId);
        String actionBody = (new Gson()).toJson(new ButtonAction("pressed:addToCard|" + productId));
        ViberButton button = new ViberButton(actionBody);
        button.setText(buildText("Додати", Colors.WHITE));
        button.setBgColor(RED);
        button.setColumns(6);
        button.setRows(tileStyle.getButtonHeight());
        button.setTextSize(tileStyle.getTextSize());
        button.setTextHAlign(ViberButton.TextAlign.MIDDLE);
        button.setSilent(true);
        return button;
    }


    private String buildText(String label, String color) {
        return "<font color='" + color + "'>" + label + "</font>";
    }

}

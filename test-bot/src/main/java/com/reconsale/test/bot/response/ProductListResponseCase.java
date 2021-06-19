package com.reconsale.test.bot.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.integration.viber.ViberBot;
import com.reconsale.bot.integration.viber.ViberBotManager;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.ButtonActionType;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.response.styling.MenuStyle;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.reconsale.bot.model.constant.Colors.GREY;
import static com.reconsale.test.bot.constant.MenuItems.MY_BUYS;

@Slf4j
@Component
public class ProductListResponseCase extends AbstractResponseCase {

    private MenuStyle defaultMenuStyle = new MenuStyle(true, GREY);

    @Override
    public Object provideResponse(Response response) {
        if (Objects.nonNull(response.getUser())) {
            ViberBot viberBot = ViberBotManager.viberBot(botToken);
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
        return Objects.nonNull(response) && MY_BUYS.equals(response.getReference());
    }

    protected ViberKeyboard fromMenu(Menu menu) {
        if (!Objects.isNull(menu) && !CollectionUtils.isEmpty(menu.getButtons())) {
            List<Button> buttons = menu.getButtons();
            List<ViberButton> viberButtons;
            if (CollectionUtils.isEmpty(buttons)) {
                viberButtons = Collections.emptyList();
            } else {
                viberButtons = buttons.stream().map(this::fromButton).collect(Collectors.toList());
            }
            MenuStyle menuStyle = menu.getMenuStyle();
            if (Objects.isNull(menuStyle)) {
                menuStyle = defaultMenuStyle;
            }

            ViberKeyboard viberKeyboard = new ViberKeyboard();
            viberKeyboard.setDefaultHeight(!menuStyle.isMinimalHeight());
            viberKeyboard.setBgColor(menuStyle.getBackgroundColor());
            viberButtons.forEach(viberKeyboard::addButton);
            viberKeyboard.setInputFieldState(ButtonContainer.InputFieldState.HIDDEN);
            return viberKeyboard;
        } else {
            return null;
        }
    }

    private ViberButton fromButton(Button button) {
        ButtonStyle buttonStyle = button.getButtonStyle();

        ViberButton viberButton = new ViberButton(button.getAction());
        viberButton.setActionType(this.getActionType(button));
        viberButton.setBgColor(buttonStyle.getBackgroundColor());
        viberButton.setTextSize(buttonStyle.getTextSize());
        String buttonText = button.getId();
        if (button.getId().equals("empty_list")){
            buttonText = "Передзамовити";
        }
        if (button.getId().equals("main_menu")){
            buttonText = "Назад до головного меню";
        }
        if (button.getId().equals("plus")){
            buttonText = "+1";
        }
        if (button.getId().equals("minus")){
            buttonText = "-1";
        }
        viberButton.setText(this.buildText(buttonText, buttonStyle.getTextColor()));
        viberButton.setColumns(buttonStyle.getWidth());
        viberButton.setRows(1);
        viberButton.setSilent(true);
        return viberButton;
    }

    private BtnActionType getActionType(Button button) {
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

    private String buildText(String label, String color) {
        return "<font color='" + color + "'>" + label + "</font>";
    }
}

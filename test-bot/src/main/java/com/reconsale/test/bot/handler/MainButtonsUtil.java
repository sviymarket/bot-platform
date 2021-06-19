package com.reconsale.test.bot.handler;

import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

import java.util.Arrays;
import java.util.List;

public class MainButtonsUtil {

    public static ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 3);

    public static List<Button> mainMenuButtons() {
        Button b1 = Button.navigationButton(MenuItems.MY_CARD, buttonStyle);
        Button b2 = Button.navigationButton(MenuItems.MY_BONUSES, buttonStyle);
        Button b3 = Button.navigationButton(MenuItems.PERSONAL_PROPOSALS, buttonStyle);
        Button b4 = Button.navigationButton(MenuItems.MY_BUYS, buttonStyle);
        Button b5 = Button.navigationButton(MenuItems.SEARCH, buttonStyle);
        Button b6 = Button.navigationButton(MenuItems.INVITE_FRIEND, buttonStyle);
        Button b7 = Button.navigationButton(MenuItems.CALL, buttonStyle);
        Button b8 = Button.navigationButton(MenuItems.LOCATION, buttonStyle);
        return Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8);
    }
}

package com.reconsale.test.bot.handler;

import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import com.reconsale.test.bot.constant.GlobalColors;
import com.reconsale.test.bot.constant.MenuItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonListUtil {

    public static ButtonStyle buttonStyle_listItem = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 4);
    public static ButtonStyle buttonStyle_plus = new ButtonStyle("#33FF36", ViberButton.TextSize.MEDIUM, GlobalColors.BLACK, 1);
    public static ButtonStyle buttonStyle_minus = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, GlobalColors.BLACK, 1);

    public static List<Button> createMenuButtonsFromList(List<String> productNames) {
        List<Button> buttons = new ArrayList<>();
        for (String productName : productNames) {
            Button b = Button.navigationButton(productName, buttonStyle_listItem);
            Button plus = Button.navigationButton("plus", buttonStyle_plus);
            Button minus = Button.navigationButton("minus", buttonStyle_minus);
            buttons.addAll(Arrays.asList(b, plus, minus));
        }
        return buttons;
    }

    public static Button createBackToMainMenuButton() {
        ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);
        return Button.navigationButton(MenuItems.MAIN_MENU, buttonStyle);
    }

    public static Button createNoElementsInListButton() {
        ButtonStyle buttonStyle = new ButtonStyle(GlobalColors.PURPLE, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);
        return Button.navigationButton("empty_list", buttonStyle);
    }

}

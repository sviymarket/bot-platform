package com.reconsale.bot.test.handler;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.constant.Colors;
import com.reconsale.bot.model.response.Button;
import com.reconsale.bot.model.response.Menu;
import com.reconsale.bot.model.response.styling.ButtonStyle;
import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.BtnActionType;
import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;
import com.reconsale.bot.model.viber.output.keyboard.ViberButton;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.reconsale.bot.model.constant.Buttons.PRESSED;
import static com.reconsale.bot.test.constant.MenuItems.MAIN_MENU;
import static com.reconsale.bot.test.constant.MenuItems.PROMOTIONS;


@Component
public class PromotionsHandler implements Handler {

    @Override
    public Response handle(Request request) {
        RichMedia richMedia = new RichMedia();
        richMedia.setBgColor(Colors.GREY);
        richMedia.setDefaultHeight(true);
        richMedia.setButtonsGroupColumns(6);
        richMedia.setButtonsGroupRows(7);
        richMedia.setInputFieldState(ButtonContainer.InputFieldState.REGULAR);

        ViberButton b1 = new ViberButton("https://www.google.com");
        b1.setColumns(6);
        b1.setRows(3);
        b1.setText("product1");
        b1.setActionType(BtnActionType.OPEN_URL);
        b1.setImage("https://static.tildacdn.com/tild3530-6164-4632-a634-646265343963/r-Merlot.jpg");
        b1.setBgColor(Colors.RED);
        b1.setTextSize(ViberButton.TextSize.LARGE);
        b1.setTextHAlign(ViberButton.TextAlign.LEFT);

        ViberButton b2 = new ViberButton("https://www.google.com");
        b2.setColumns(6);
        b2.setRows(2);
        b2.setText("product2");
        b2.setActionType(BtnActionType.OPEN_URL);
        b2.setImage("https://images.av.ru/av.ru/product/h8b/h16/9061469683742.jpg");
        b2.setBgColor(Colors.RED);
        b2.setTextSize(ViberButton.TextSize.LARGE);
        b2.setTextHAlign(ViberButton.TextAlign.LEFT);

        ViberButton b3 = new ViberButton("https://www.google.com");
        b3.setColumns(6);
        b3.setRows(1);
        b3.setText("product3");
        b3.setActionType(BtnActionType.OPEN_URL);
        b3.setImage("https://www.asti.in.ua/wp-content/uploads/2018/06/Martini-Asti-075-min.jpg");
        b3.setBgColor(Colors.RED);
        b3.setTextSize(ViberButton.TextSize.LARGE);
        b3.setTextHAlign(ViberButton.TextAlign.LEFT);

        richMedia.addButton(b1);
        richMedia.addButton(b2);
        richMedia.addButton(b3);

        richMedia.addButton(b1);
        richMedia.addButton(b2);
        richMedia.addButton(b3);

        ButtonStyle buttonStyle = new ButtonStyle(Colors.RED, ViberButton.TextSize.MEDIUM, Colors.WHITE, 6);
        Button b11 = new Button(MAIN_MENU, buttonStyle);

        Menu menu = new Menu();
        menu.setButtons(Arrays.asList(b11));


        return Response.builder()
                .user(request.getUser())
                .richMedia(richMedia)
                .menu(menu)
                .reference(PROMOTIONS)
                .build();
    }

    @Override
    public String mapping() {
        return PRESSED + ":" + PROMOTIONS;
    }
}

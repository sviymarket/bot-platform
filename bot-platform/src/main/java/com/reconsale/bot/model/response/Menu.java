package com.reconsale.bot.model.response;

import com.reconsale.bot.model.response.styling.MenuStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    private String template;

    private List<Button> buttons;

    private MenuStyle menuStyle;

}

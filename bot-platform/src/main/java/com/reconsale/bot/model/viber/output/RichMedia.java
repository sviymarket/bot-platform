package com.reconsale.bot.model.viber.output;

import com.reconsale.bot.model.viber.output.keyboard.ButtonContainer;

public class RichMedia extends ButtonContainer {

    public RichMedia() {
        this.type = MessageType.CAROUSEL.getKeyName();
    }

    public RichMedia(String altText) {
        this.type = MessageType.CAROUSEL.getKeyName();
        this.altText = altText;
    }
}

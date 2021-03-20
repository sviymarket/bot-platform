package com.reconsale.viber4j.keyboard;

import com.reconsale.viber4j.outgoing.MessageType;

public class RichMedia extends ButtonContainer {

    public RichMedia() {
        this.type = MessageType.CAROUSEL.getKeyName();
    }

    public RichMedia(String altText) {
        this.type = MessageType.CAROUSEL.getKeyName();
        this.altText = altText;
    }
}

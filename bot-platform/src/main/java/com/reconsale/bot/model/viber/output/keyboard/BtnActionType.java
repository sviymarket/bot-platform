package com.reconsale.bot.model.viber.output.keyboard;

public enum BtnActionType {
    NONE("none"),
    REPLY("reply"),
    OPEN_URL("open-url"),
    SHARE_PHONE("share-phone"); //api level 3 and above

    BtnActionType(String action) {
        this.action = action;
    }

    private final String action;

    public String actionName() {
        return action;
    }
}

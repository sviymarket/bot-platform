package com.reconsale.viber4j;

import com.google.gson.JsonObject;
import com.reconsale.viber4j.utils.ViberConstants;

import org.apache.commons.lang3.StringUtils;

/**
 * Info about a message sender
 *
 * @author n.zvyagintsev
 */
public class SenderInfo {

    private final String name;
    private final String avatar;

    public SenderInfo(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public JsonObject toJson() {
        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(avatar))
            return null;
        JsonObject sender = new JsonObject();
        if (StringUtils.isNotEmpty(name))
            sender.addProperty(ViberConstants.NAME, name);
        if (StringUtils.isNotEmpty(name))
            sender.addProperty(ViberConstants.AVATAR, avatar);
        return sender;
    }
}

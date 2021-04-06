package com.reconsale.bot.model.event;

import java.util.HashMap;
import java.util.Map;

public enum EventType {

    MESSAGE("message"),
    DELIVERED("delivered"),
    SEEN("seen");

    private final String description;

    EventType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    private static final Map<String, EventType> BY_LABEL = new HashMap<>();

    static {
        for (EventType e : values()) {
            BY_LABEL.put(e.description, e);
        }
    }

    public static EventType fromDescription(String description) {
        return BY_LABEL.get(description);
    }

}

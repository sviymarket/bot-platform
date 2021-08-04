package com.reconsale.bot.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Context {

    private Map<String, String> data;
}

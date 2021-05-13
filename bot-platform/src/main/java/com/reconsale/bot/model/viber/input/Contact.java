package com.reconsale.bot.model.viber.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contact {

    @JsonProperty("phone_number")
    String phoneNumber;

}

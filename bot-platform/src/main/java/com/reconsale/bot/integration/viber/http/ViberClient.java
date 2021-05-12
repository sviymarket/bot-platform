package com.reconsale.bot.integration.viber.http;

import java.io.IOException;

public interface ViberClient {

    /**
     * Posts messageForUser to Viber
     *
     * @param message messageForUser with request for Viber
     * @param url     url of Viber endpoint
     * @return response from Viber
     * @throws IOException if response status is not 200
     */
    String post(String message, String url) throws IOException;
}

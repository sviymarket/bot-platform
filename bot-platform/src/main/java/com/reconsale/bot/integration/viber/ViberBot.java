package com.reconsale.bot.integration.viber;


import com.reconsale.bot.integration.viber.outbound.Outgoing;
import com.reconsale.bot.model.viber.account.AccountInfo;
import com.reconsale.bot.model.viber.account.UserDetails;
import com.reconsale.bot.model.viber.account.UserOnline;

import java.util.List;

public interface ViberBot {

    /**
     * Registers endpoint for receiving messages from the Viber
     *
     * @param webHookUrl endpoint for receiving messages from the Viber.
     * @return post message result
     */
    boolean setWebHook(String webHookUrl);

    boolean setWebHook(String webHookUrl, List<CallbackEvent> events);

    boolean removeWebHook();

    Outgoing messageForUser(String receiverId);

    Outgoing broadcastMessage(List<String> receiverIds);

    Outgoing publicMessage(String fromId);

    AccountInfo getAccountInfo();

    UserDetails getUserDetails(String userId);

    List<UserOnline> getUserOnline(List<String> receiverIds);

    enum CallbackEvent {
        delivered, seen, failed, subscribed, unsubscribed, conversation_started
    }
}

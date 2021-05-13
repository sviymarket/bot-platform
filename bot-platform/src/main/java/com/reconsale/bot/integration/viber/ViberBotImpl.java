package com.reconsale.bot.integration.viber;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.reconsale.bot.integration.viber.http.DefaultViberClient;
import com.reconsale.bot.integration.viber.http.ViberClient;
import com.reconsale.bot.integration.viber.outbound.Outgoing;
import com.reconsale.bot.integration.viber.outbound.OutgoingImpl;
import com.reconsale.bot.model.viber.SenderInfo;
import com.reconsale.bot.model.viber.account.AccountInfo;
import com.reconsale.bot.model.viber.account.UserDetails;
import com.reconsale.bot.model.viber.account.UserOnline;
import com.reconsale.bot.model.viber.utils.JsonUtils;
import com.reconsale.bot.model.viber.utils.ViberConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class ViberBotImpl implements ViberBot {

    private static final Logger logger = LoggerFactory.getLogger(ViberBotImpl.class);

    private final ViberClient viberClient;

    private SenderInfo sender;

    /**
     * Constructor for creating Viber bot object
     *
     * @param botToken auth token
     */
    ViberBotImpl(String botToken) {
        this.viberClient = new DefaultViberClient(botToken);
    }

    ViberBotImpl(String botToken, SenderInfo sender) {
        this.sender = sender;
        this.viberClient = new DefaultViberClient(botToken);
    }

    @Override
    public boolean setWebHook(String webHookUrl) {
        return setWebHook(webHookUrl, Arrays.asList(CallbackEvent.values()));
    }

    @Override
    public boolean setWebHook(String webHookUrl, List<CallbackEvent> events) {
        JsonObject message = new JsonObject();
        message.addProperty("url", webHookUrl);
        JsonArray eventsArray = new JsonArray();
        Stream.of(CallbackEvent.values()).forEach(
                viberEvent -> eventsArray.add(viberEvent.name()));
        message.add(ViberConstants.EVENT_TYPES, eventsArray);
        String response = null;
        Integer status = null;
        try {
            response = viberClient.post(
                    message.toString(), ViberConstants.VIBER_REGISTRATION_URL);
            logger.info("Registration web-hook {} response {}", webHookUrl, response);
            status = JsonUtils.getJsonValueInt(JsonUtils.parseString(response), ViberConstants.STATUS);

        } catch (IOException e) {
            logger.error("Web-hook {} registration failed {}", webHookUrl, e);
        }
        return Objects.nonNull(status) && status.equals(0);
    }

    public boolean removeWebHook() {
        JsonObject message = new JsonObject();
        message.addProperty("url", StringUtils.EMPTY);
        String response = null;
        try {
            response = viberClient.post(
                    message.toString(), ViberConstants.VIBER_REGISTRATION_URL);
            logger.error("Removing web-hook response {}", message);
        } catch (IOException e) {
            logger.error("Removing web-hook error {}", e);
        }
        return StringUtils.isNotEmpty(response);
    }

    @Override
    public Outgoing messageForUser(String receiverId) {
        return OutgoingImpl.outgoingForReceiver(receiverId, viberClient, sender);
    }

    @Override
    public Outgoing broadcastMessage(List<String> receiverIds) {
        return OutgoingImpl.outgoingForBroadcast(receiverIds, viberClient, sender);
    }

    @Override
    public Outgoing publicMessage(String fromId) {
        return OutgoingImpl.outgoingForPublic(fromId, viberClient, sender);
    }

    @Override
    public AccountInfo getAccountInfo() {
        String response;
        try {
            response = viberClient.post("{}", ViberConstants.VIBER_ACC_INFO_URL);
        } catch (IOException e) {
            return AccountInfo.EMPTY;
        }
        return AccountInfo.fromStr(response);
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        String request = "{\"id\":\"" + userId + "\"}";
        String response;
        try {
            response = viberClient.post(request, ViberConstants.VIBER_USER_DETAILS_URL);
        } catch (IOException e) {
            return UserDetails.EMPTY;
        }
        return UserDetails.fromString(response);
    }

    @Override
    public List<UserOnline> getUserOnline(List<String> receiverIds) {
        StringBuilder request = new StringBuilder("{\"ids\":[").append(
                StringUtils.join(receiverIds, ',')).append("]}");
        String response;
        try {
            response = viberClient.post(request.toString(), ViberConstants.VIBER_USER_ONLINE_URL);
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
        return UserOnline.fromString(response);
    }
}
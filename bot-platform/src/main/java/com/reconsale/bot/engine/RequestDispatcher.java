package com.reconsale.bot.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import com.reconsale.bot.model.request.Payload;
import com.reconsale.bot.model.response.ButtonAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.reconsale.bot.constant.BeansNaming.HANDLERS_MAP;
import static com.reconsale.bot.constant.HandlerKeys.CONVERSATION_STARTED_HANDLER_KEY;

@Slf4j
@Component
public class RequestDispatcher {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier(HANDLERS_MAP)
    private Map<String, Handler> mappings;

    public Response dispatch(Request request) {
        log.debug("Dispatching request: " + request);
        String key = toKey(request);
        log.info("Handler key: " + key);

        log.debug("Got mappings: " + mappings);
        Handler handler = mappings.get(key);
        if (Objects.isNull(handler)) {
            handler = mappings.get(CONVERSATION_STARTED_HANDLER_KEY);
        }

        log.info("Got handler: " + handler);
        return handler.handle(request);
    }

    private String toKey(Request request) {
        String key = null;
        if (Objects.nonNull(request) && Objects.nonNull(request.getPayload())) {
            Payload payload = request.getPayload();
            String content = payload.getContent();
            if (Objects.nonNull(content)) {
                ButtonAction buttonAction = getButtonAction(content);
                if (Objects.nonNull(buttonAction)) {
                    log.info("User pressed a button! " + buttonAction.getData());
                    return buttonAction.getData();
                }
            }


            String contentType = payload.getContentType();
            String eventType = payload.getEventType();

            key = (StringUtils.isNotEmpty(contentType) ? contentType : "undefined") + "-" + eventType;
        }
        return key;

    }

    private ButtonAction getButtonAction(String inputString) {
        ButtonAction buttonAction = null;
        try {
            buttonAction = objectMapper.readValue(inputString, ButtonAction.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return buttonAction;
    }

}

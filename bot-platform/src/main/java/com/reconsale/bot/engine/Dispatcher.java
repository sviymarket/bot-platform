package com.reconsale.bot.engine;

import com.reconsale.bot.engine.handler.Handler;
import com.reconsale.bot.engine.handler.TextMessageHandler;
import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class Dispatcher {

    private Map<String, Handler> mappings;

    @Autowired
    private TextMessageHandler textMessageHandler;

    public Dispatcher() {
        this.mappings = new HashMap<>();
    }

    @PostConstruct
    private void initHandlers() {
        this.mappings.put("text", textMessageHandler);
    }

    public Response dispatch(Request request) {
        log.info("Dispatching request: " + request);
        if (Objects.nonNull(request) && Objects.nonNull(request.getPayload())) {
            String handlerKey = request.getPayload().getContentType();
            if (!mappings.containsKey(handlerKey)) {
                log.info("could not find handler for request: " + request);
            }
            return mappings.get(handlerKey).handle(request);
        }
        log.info("Can not handle empty request: " + request);
        return null;
    }

}

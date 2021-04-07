package com.reconsale.bot.engine;

import com.reconsale.bot.model.Request;
import com.reconsale.bot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static com.reconsale.bot.constant.BeansNaming.HANDLERS_MAP;

@Slf4j
@Component
public class Dispatcher {

    public static final String DEFAULT_HANDLER_KEY = "*";

    @Autowired
    @Qualifier(HANDLERS_MAP)
    private Map<String, Handler> mappings;

    public Response dispatch(Request request) {
        log.info("Dispatching request: " + request);
        String key = toKey(request);
        log.info("Handler key: " + key);
        Handler h = mappings.get(key);
        log.info("Got mappings: " + mappings);
        log.info("Got handler: " + h);
        return mappings.get(key).handle(request);
    }

    private String toKey(Request request) {
        String key = DEFAULT_HANDLER_KEY;
        if (Objects.nonNull(request) && Objects.nonNull(request.getPayload())) {
            key = request.getPayload().getContentType();
        }
        return key;
    }

}

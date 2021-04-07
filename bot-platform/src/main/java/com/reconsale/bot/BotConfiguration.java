package com.reconsale.bot;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.integration.ResponseCase;
import com.reconsale.viber4j.ViberBotManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class BotConfiguration {

    @Bean
    public ViberBotManager viberBotManager() {
        return new ViberBotManager();
    }

    @Bean
    public Map<String, Handler> handlers(ApplicationContext context) {
        Map<String, Handler> beans = context.getBeansOfType(Handler.class);

        Map<String, Handler> result = new HashMap<String, Handler>();

        List<Handler> beanList = new ArrayList<>(beans.values());
        Collections.sort(beanList, org.springframework.core.annotation.AnnotationAwareOrderComparator.INSTANCE);

        for (Handler handler : beanList) {
            if (!result.containsKey(handler.mapping())) {
                result.put(handler.mapping(), handler);
            }
        }

        return result;
    }

    @Bean
    public List<ResponseCase<?>> responseCases(ApplicationContext context) {
        Map<String, ResponseCase> beans = context.getBeansOfType(ResponseCase.class);
        ArrayList<ResponseCase<?>> beanList = new ArrayList(beans.values());
        Collections.sort(beanList, org.springframework.core.annotation.AnnotationAwareOrderComparator.INSTANCE);
        return beanList;
    }
}

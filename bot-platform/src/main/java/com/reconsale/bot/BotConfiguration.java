package com.reconsale.bot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reconsale.bot.engine.handler.Handler;
import com.reconsale.bot.integration.ResponseCase;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Configuration
@ComponentScan("com.reconsale.bot")
public class BotConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
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
        //ArrayList<ResponseCase<?>> beanList = new ArrayList<ResponseCase<?>>((Collection<? extends ResponseCase<?>>) beans.values());
        ArrayList<ResponseCase<?>> beanList = new ArrayList<ResponseCase<?>>();
        Collections.sort(beanList, org.springframework.core.annotation.AnnotationAwareOrderComparator.INSTANCE);
        return beanList;
    }

}

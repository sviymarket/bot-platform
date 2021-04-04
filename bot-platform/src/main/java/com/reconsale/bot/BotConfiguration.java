package com.reconsale.bot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.reconsale.bot.engine.Handler;
import com.reconsale.bot.integration.ResponseCase;

@ComponentScan("com.reconsale.bot")
public class BotConfiguration {
	
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
		ArrayList<ResponseCase<?>> beanList = new ArrayList<ResponseCase<?>>((Collection<? extends ResponseCase<?>>) beans.values());
		Collections.sort(beanList, org.springframework.core.annotation.AnnotationAwareOrderComparator.INSTANCE);
		return beanList;
	}
}

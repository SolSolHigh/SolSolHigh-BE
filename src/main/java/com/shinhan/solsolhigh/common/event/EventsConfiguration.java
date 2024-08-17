package com.shinhan.solsolhigh.common.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public InitializingBean setPublisher() {
        return () -> Events.setPublisher(applicationContext);
    }
}

package com.example.api.config;

import com.example.common.bus.EventToCommandBus;
import com.example.domain.spi.EventPublisher;
import com.example.infra.adapter.event.EventPublisherAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Bean
    public EventPublisher eventPublisher(
            ApplicationEventPublisher applicationEventPublisher,
            EventToCommandBus eventToCommandBus) {
        return new EventPublisherAdapter(applicationEventPublisher, eventToCommandBus);
    }
}


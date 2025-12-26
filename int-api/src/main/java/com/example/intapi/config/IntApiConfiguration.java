package com.example.intapi.config;

import com.example.common.bus.EventToCommandBus;
import com.example.domain.spi.EventPublisher;
import com.example.infra.adapter.event.EventPublisherAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration cho Internal API module.
 */
@Configuration
public class IntApiConfiguration {

    /**
     * Wire EventPublisherAdapter vào EventPublisher SPI.
     * Tích hợp với EventToCommandBus để hỗ trợ cơ chế Domain Event fire đến CommandHandler.
     */
    @Bean
    public EventPublisher eventPublisher(
            ApplicationEventPublisher applicationEventPublisher,
            EventToCommandBus eventToCommandBus) {
        return new EventPublisherAdapter(applicationEventPublisher, eventToCommandBus);
    }
}


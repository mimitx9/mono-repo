package com.example.jobexecutor.config;

import com.example.domain.spi.EventPublisher;
import com.example.infra.adapter.event.EventPublisherAdapter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration cho Job Executor module.
 */
@Configuration
public class JobExecutorConfiguration {

    /**
     * Wire EventPublisherAdapter vào EventPublisher SPI.
     */
    @Bean
    public EventPublisher eventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new EventPublisherAdapter(applicationEventPublisher);
    }
}


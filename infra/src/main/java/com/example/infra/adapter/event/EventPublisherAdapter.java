package com.example.infra.adapter.event;

import com.example.domain.event.DomainEvent;
import com.example.domain.spi.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Adapter cho Event Publishing sử dụng Spring ApplicationEventPublisher.
 * Đây là một Adapter trong Hexagonal Architecture, implement EventPublisher SPI.
 * 
 * Có thể thay thế bằng Kafka, RabbitMQ, hoặc các message broker khác.
 */
public class EventPublisherAdapter implements EventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(EventPublisherAdapter.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        logger.debug("Publishing domain event: {} with ID: {}", event.getClass().getSimpleName(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }
}


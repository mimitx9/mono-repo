package com.example.domain.spi;

import com.example.domain.event.DomainEvent;

/**
 * SPI (Service Provider Interface) cho Event Publisher.
 * Đây là một Port trong Hexagonal Architecture, định nghĩa contract cho event publishing.
 * Implementation sẽ được cung cấp bởi Infra module (Adapter).
 */
public interface EventPublisher {
    /**
     * Publish một domain event.
     * 
     * @param event Event cần publish
     */
    void publish(DomainEvent event);

    /**
     * Publish nhiều domain events.
     * 
     * @param events Các events cần publish
     */
    default void publishAll(DomainEvent... events) {
        for (DomainEvent event : events) {
            publish(event);
        }
    }
}


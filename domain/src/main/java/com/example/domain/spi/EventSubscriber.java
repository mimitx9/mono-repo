package com.example.domain.spi;

import com.example.domain.event.DomainEvent;

/**
 * SPI (Service Provider Interface) cho Event Subscriber.
 * Đây là một Port trong Hexagonal Architecture, định nghĩa contract cho event subscription.
 * Implementation sẽ được cung cấp bởi Infra module (Adapter).
 * 
 * @param <T> Loại Domain Event mà subscriber này lắng nghe
 */
public interface EventSubscriber<T extends DomainEvent> {
    /**
     * Xử lý một domain event.
     * 
     * @param event Event cần xử lý
     */
    void handle(T event);

    /**
     * Lấy loại event mà subscriber này lắng nghe.
     * 
     * @return Class của event type
     */
    Class<T> getEventType();
}


package com.example.infra.adapter.event.strategy;

import com.example.domain.event.DomainEvent;

/**
 * Strategy interface cho Event Publishing.
 * Strategy Pattern cho phép thay đổi thuật toán publish event mà không thay đổi client code.
 * 
 * Các implementations có thể là:
 * - SpringEventsPublishingStrategy: Sử dụng Spring ApplicationEventPublisher
 * - KafkaPublishingStrategy: Publish events lên Kafka
 * - RabbitMQPublishingStrategy: Publish events lên RabbitMQ
 * - InMemoryPublishingStrategy: Publish events trong memory (cho testing)
 */
public interface PublishingStrategy {
    
    /**
     * Publish một domain event.
     * 
     * @param event Event cần publish
     */
    void publish(DomainEvent event);
    
    /**
     * Kiểm tra xem strategy này có sẵn sàng để sử dụng không.
     * 
     * @return true nếu strategy sẵn sàng, false nếu không
     */
    default boolean isAvailable() {
        return true;
    }
    
    /**
     * Tên của strategy (để logging/debugging).
     * 
     * @return Tên strategy
     */
    String getStrategyName();
}


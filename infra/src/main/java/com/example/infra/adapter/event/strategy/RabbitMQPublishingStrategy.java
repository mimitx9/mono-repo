package com.example.infra.adapter.event.strategy;

import com.example.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy implementation sử dụng RabbitMQ để publish events.
 * 
 * Note: Đây là mock implementation. Trong thực tế, cần:
 * - Inject RabbitTemplate hoặc AmqpTemplate
 * - Configure exchange và routing keys
 * - Handle serialization
 * - Handle errors và retries
 */
public class RabbitMQPublishingStrategy implements PublishingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQPublishingStrategy.class);
    
    private final String exchange;
    private final List<DomainEvent> publishedEvents; // For testing purposes
    
    // Trong thực tế, sẽ inject RabbitTemplate:
    // private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublishingStrategy(String exchange) {
        this.exchange = exchange;
        this.publishedEvents = new ArrayList<>();
    }

    @Override
    public void publish(DomainEvent event) {
        logger.info("Publishing event {} to RabbitMQ exchange: {} with routing key: {}", 
            event.getClass().getSimpleName(), 
            exchange,
            event.getClass().getSimpleName());
        
        // Mock implementation - trong thực tế sẽ là:
        // String routingKey = event.getClass().getSimpleName();
        // rabbitTemplate.convertAndSend(exchange, routingKey, event);
        
        publishedEvents.add(event);
        logger.debug("Event {} published to RabbitMQ successfully", event.getClass().getSimpleName());
    }

    @Override
    public boolean isAvailable() {
        // Trong thực tế, kiểm tra RabbitMQ connection
        // return rabbitTemplate != null && rabbitTemplate.getConnectionFactory() != null;
        return true;
    }

    @Override
    public String getStrategyName() {
        return "RabbitMQ";
    }
    
    /**
     * Get published events (for testing purposes).
     */
    public List<DomainEvent> getPublishedEvents() {
        return new ArrayList<>(publishedEvents);
    }
}


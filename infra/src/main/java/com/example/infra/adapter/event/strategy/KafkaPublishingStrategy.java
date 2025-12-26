package com.example.infra.adapter.event.strategy;

import com.example.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy implementation sử dụng Kafka để publish events.
 * 
 * Note: Đây là mock implementation. Trong thực tế, cần:
 * - Inject KafkaTemplate hoặc KafkaProducer
 * - Configure topic names
 * - Handle serialization
 * - Handle errors và retries
 */
public class KafkaPublishingStrategy implements PublishingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaPublishingStrategy.class);
    
    private final String topicPrefix;
    private final List<DomainEvent> publishedEvents; // For testing purposes
    
    // Trong thực tế, sẽ inject KafkaTemplate:
    // private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

    public KafkaPublishingStrategy(String topicPrefix) {
        this.topicPrefix = topicPrefix;
        this.publishedEvents = new ArrayList<>();
    }

    @Override
    public void publish(DomainEvent event) {
        logger.info("Publishing event {} to Kafka topic: {}.{}", 
            event.getClass().getSimpleName(), 
            topicPrefix, 
            event.getClass().getSimpleName());
        
        // Mock implementation - trong thực tế sẽ là:
        // String topic = topicPrefix + "." + event.getClass().getSimpleName();
        // kafkaTemplate.send(topic, event.getEventId().toString(), event);
        
        publishedEvents.add(event);
        logger.debug("Event {} published to Kafka successfully", event.getClass().getSimpleName());
    }

    @Override
    public boolean isAvailable() {
        // Trong thực tế, kiểm tra Kafka connection
        // return kafkaTemplate != null && kafkaTemplate.getProducerFactory() != null;
        return true;
    }

    @Override
    public String getStrategyName() {
        return "Kafka";
    }
    
    /**
     * Get published events (for testing purposes).
     */
    public List<DomainEvent> getPublishedEvents() {
        return new ArrayList<>(publishedEvents);
    }
}


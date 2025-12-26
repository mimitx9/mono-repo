package com.example.eventprocessor.subscriber.example;

import com.example.domain.example.event.UserCreatedEvent;
import com.example.eventprocessor.subscriber.ExternalEventSubscriber;
import org.springframework.stereotype.Component;

/**
 * Ví dụ External Event Subscriber.
 * Subscriber này lắng nghe UserCreatedEvent từ external systems.
 * 
 * Trong thực tế, có thể subscribe từ:
 * - Kafka topics
 * - RabbitMQ queues
 * - SQS queues
 * - etc.
 */
@Component
public class ExampleExternalEventSubscriber extends ExternalEventSubscriber<UserCreatedEvent> {

    @Override
    protected void processEvent(UserCreatedEvent event) {
        // Xử lý event từ external system
        // Ví dụ: sync data, update cache, send notifications, etc.
        logger.info("Processing external UserCreatedEvent: UserId={}, Name={}, Email={}", 
            event.getUserId(), event.getUserName(), event.getUserEmail());
        
        // Có thể gọi CommandBus để thực hiện business logic
        // commandBus.send(new SyncUserCommand(...));
    }

    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }
}


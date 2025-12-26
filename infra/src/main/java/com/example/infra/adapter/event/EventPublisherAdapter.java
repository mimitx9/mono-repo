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
 * Adapter này cũng hỗ trợ tích hợp với EventToCommandBus (từ common module)
 * để cho phép Domain Event có thể "fire" đến CommandHandler thông qua cơ chế 
 * event-to-command conversion.
 * 
 * Note: EventToCommandBus được inject qua reflection để tránh circular dependency
 * giữa infra và common modules. Nếu không có EventToCommandBus, event vẫn được publish bình thường.
 * 
 * Có thể thay thế bằng Kafka, RabbitMQ, hoặc các message broker khác.
 */
public class EventPublisherAdapter implements EventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(EventPublisherAdapter.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Object eventToCommandBus; // Sử dụng Object để tránh dependency vào common module

    public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventToCommandBus = null;
    }

    public EventPublisherAdapter(
            ApplicationEventPublisher applicationEventPublisher,
            Object eventToCommandBus) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventToCommandBus = eventToCommandBus;
    }

    @Override
    public void publish(DomainEvent event) {
        logger.debug("Publishing domain event: {} with ID: {}", event.getClass().getSimpleName(), event.getEventId());
        
        // Publish event để các subscribers có thể lắng nghe
        applicationEventPublisher.publishEvent(event);
        
        // Nếu có EventToCommandBus, thử convert event thành command
        if (eventToCommandBus != null) {
            try {
                // Sử dụng reflection để gọi method handleEvent
                java.lang.reflect.Method handleMethod = eventToCommandBus.getClass()
                    .getMethod("handleEvent", DomainEvent.class);
                handleMethod.invoke(eventToCommandBus, event);
            } catch (Exception e) {
                logger.warn("Failed to convert event {} to command, but event was published successfully", 
                    event.getClass().getSimpleName(), e);
                // Không throw exception vì event đã được publish thành công
            }
        }
    }
}


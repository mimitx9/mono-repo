package com.example.infra.adapter.event;

import com.example.domain.event.DomainEvent;
import com.example.domain.spi.EventPublisher;
import com.example.infra.adapter.event.strategy.PublishingStrategy;
import com.example.infra.adapter.event.strategy.SpringEventsPublishingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Adapter cho Event Publishing sử dụng Strategy Pattern.
 * Đây là một Adapter trong Hexagonal Architecture, implement EventPublisher SPI.
 * 
 * Adapter này sử dụng Strategy Pattern để có thể thay đổi cách publish events:
 * - SpringEventsPublishingStrategy: Sử dụng Spring ApplicationEventPublisher (mặc định)
 * - KafkaPublishingStrategy: Publish events lên Kafka
 * - RabbitMQPublishingStrategy: Publish events lên RabbitMQ
 * - CompositePublishingStrategy: Publish events đến nhiều strategies cùng lúc
 * 
 * Adapter này cũng hỗ trợ tích hợp với EventToCommandBus (từ common module)
 * để cho phép Domain Event có thể "fire" đến CommandHandler thông qua cơ chế 
 * event-to-command conversion.
 * 
 * Note: EventToCommandBus được inject qua reflection để tránh circular dependency
 * giữa infra và common modules. Nếu không có EventToCommandBus, event vẫn được publish bình thường.
 */
public class EventPublisherAdapter implements EventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(EventPublisherAdapter.class);
    
    private final PublishingStrategy publishingStrategy;
    private final Object eventToCommandBus; // Sử dụng Object để tránh circular dependency vào common module

    /**
     * Constructor với ApplicationEventPublisher (backward compatibility).
     * Tự động tạo SpringEventsPublishingStrategy.
     * 
     * @param applicationEventPublisher Spring ApplicationEventPublisher
     */
    public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.publishingStrategy = new SpringEventsPublishingStrategy(applicationEventPublisher);
        this.eventToCommandBus = null;
    }

    /**
     * Constructor với ApplicationEventPublisher và EventToCommandBus (backward compatibility).
     * Tự động tạo SpringEventsPublishingStrategy.
     * 
     * @param applicationEventPublisher Spring ApplicationEventPublisher
     * @param eventToCommandBus EventToCommandBus để convert events thành commands
     */
    public EventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher, Object eventToCommandBus) {
        this.publishingStrategy = new SpringEventsPublishingStrategy(applicationEventPublisher);
        this.eventToCommandBus = eventToCommandBus;
    }

    /**
     * Constructor với PublishingStrategy.
     * 
     * @param publishingStrategy Strategy để publish events
     */
    public EventPublisherAdapter(PublishingStrategy publishingStrategy) {
        this.publishingStrategy = publishingStrategy;
        this.eventToCommandBus = null;
    }

    /**
     * Constructor với PublishingStrategy và EventToCommandBus.
     * 
     * @param publishingStrategy Strategy để publish events
     * @param eventToCommandBus EventToCommandBus để convert events thành commands
     */
    public EventPublisherAdapter(PublishingStrategy publishingStrategy, Object eventToCommandBus) {
        this.publishingStrategy = publishingStrategy;
        this.eventToCommandBus = eventToCommandBus;
    }

    @Override
    public void publish(DomainEvent event) {
        logger.debug("Publishing domain event: {} with ID: {} using strategy: {}", 
            event.getClass().getSimpleName(), 
            event.getEventId(),
            publishingStrategy.getStrategyName());
        
        // Publish event sử dụng strategy
        publishingStrategy.publish(event);
        
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

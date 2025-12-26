package com.example.eventprocessor.subscriber;

import com.example.domain.event.DomainEvent;
import com.example.infra.adapter.event.EventSubscriberAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Base class cho các External Event Subscribers.
 * Các subscribers này lắng nghe events từ external systems (Kafka, RabbitMQ, etc.)
 * và xử lý chúng.
 * 
 * @param <T> Loại External Event
 */
public abstract class ExternalEventSubscriber<T extends DomainEvent> 
        extends EventSubscriberAdapter<T> {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(T event) {
        logger.info("Processing external event: {} with ID: {}", 
            event.getClass().getSimpleName(), event.getEventId());
        
        try {
            processEvent(event);
        } catch (Exception e) {
            logger.error("Error processing external event: {}", event.getEventId(), e);
            handleError(event, e);
        }
    }
    
    @Override
    public abstract Class<T> getEventType();

    /**
     * Xử lý event. Subclasses phải implement method này.
     * 
     * @param event Event cần xử lý
     */
    protected abstract void processEvent(T event);

    /**
     * Xử lý lỗi khi process event. Có thể override để implement retry logic, dead letter queue, etc.
     * 
     * @param event Event gây lỗi
     * @param error Exception xảy ra
     */
    protected void handleError(T event, Exception error) {
        // Default: chỉ log error
        // Có thể implement retry, dead letter queue, etc.
    }
}


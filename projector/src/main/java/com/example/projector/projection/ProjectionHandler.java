package com.example.projector.projection;

import com.example.domain.event.DomainEvent;
import com.example.infra.adapter.event.EventSubscriberAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class cho các Projection Handlers.
 * Projection Handlers lắng nghe domain events và update read models/views.
 * 
 * @param <T> Loại Domain Event mà projection này xử lý
 */
public abstract class ProjectionHandler<T extends DomainEvent> 
        extends EventSubscriberAdapter<T> {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(T event) {
        logger.debug("Processing projection for event: {} with ID: {}", 
            event.getClass().getSimpleName(), event.getEventId());
        
        try {
            project(event);
        } catch (Exception e) {
            logger.error("Error processing projection for event: {}", event.getEventId(), e);
            handleError(event, e);
        }
    }
    
    @Override
    public abstract Class<T> getEventType();

    /**
     * Update projection/view dựa trên event.
     * Subclasses phải implement method này.
     * 
     * @param event Event cần xử lý
     */
    protected abstract void project(T event);

    /**
     * Xử lý lỗi khi project. Có thể override để implement retry logic, etc.
     * 
     * @param event Event gây lỗi
     * @param error Exception xảy ra
     */
    protected void handleError(T event, Exception error) {
        // Default: chỉ log error
        // Có thể implement retry, dead letter queue, etc.
    }
}


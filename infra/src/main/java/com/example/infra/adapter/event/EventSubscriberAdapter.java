package com.example.infra.adapter.event;

import com.example.domain.event.DomainEvent;
import com.example.domain.spi.EventSubscriber;
import org.springframework.context.event.EventListener;

/**
 * Base adapter cho Event Subscription.
 * Đây là một Adapter trong Hexagonal Architecture, implement EventSubscriber SPI.
 * 
 * @param <T> Loại Domain Event mà adapter này lắng nghe
 */
public abstract class EventSubscriberAdapter<T extends DomainEvent> implements EventSubscriber<T> {
    
    @EventListener
    @SuppressWarnings("unchecked")
    public void onDomainEvent(DomainEvent event) {
        if (getEventType().isInstance(event)) {
            handle((T) event);
        }
    }
    
    // Abstract methods để subclasses implement
    public abstract void handle(T event);
    
    public abstract Class<T> getEventType();
}


package com.example.infra.adapter.event;

import com.example.domain.example.event.UserCreatedEvent;
import com.example.domain.spi.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Ví dụ Event Subscriber - UserCreatedEventSubscriber.
 * Subscriber này lắng nghe UserCreatedEvent và xử lý (ví dụ: gửi email, log, etc.).
 */
@Component
public class UserCreatedEventSubscriber extends EventSubscriberAdapter<UserCreatedEvent> 
        implements EventSubscriber<UserCreatedEvent> {
    
    private static final Logger logger = LoggerFactory.getLogger(UserCreatedEventSubscriber.class);

    @Override
    public void handle(UserCreatedEvent event) {
        logger.info("User created: ID={}, Name={}, Email={}", 
            event.getUserId(), event.getUserName(), event.getUserEmail());
        
        // Có thể thêm logic khác như:
        // - Gửi welcome email
        // - Tạo audit log
        // - Update cache
        // - etc.
    }

    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }
}


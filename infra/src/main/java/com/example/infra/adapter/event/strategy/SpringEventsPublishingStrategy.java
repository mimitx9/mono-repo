package com.example.infra.adapter.event.strategy;

import com.example.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Strategy implementation sử dụng Spring ApplicationEventPublisher.
 * Đây là strategy mặc định cho in-process event publishing.
 */
public class SpringEventsPublishingStrategy implements PublishingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(SpringEventsPublishingStrategy.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringEventsPublishingStrategy(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        logger.debug("Publishing event {} using Spring Events strategy", event.getClass().getSimpleName());
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public String getStrategyName() {
        return "SpringEvents";
    }
}


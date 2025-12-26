package com.example.infra.adapter.event.strategy;

import com.example.domain.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Strategy - Publish events đến nhiều strategies cùng lúc.
 * Hữu ích khi muốn publish events đến cả Spring Events và message broker.
 */
public class CompositePublishingStrategy implements PublishingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(CompositePublishingStrategy.class);
    
    private final List<PublishingStrategy> strategies;

    public CompositePublishingStrategy(List<PublishingStrategy> strategies) {
        this.strategies = new ArrayList<>(strategies);
    }

    @Override
    public void publish(DomainEvent event) {
        logger.debug("Publishing event {} using {} strategies", 
            event.getClass().getSimpleName(), 
            strategies.size());
        
        for (PublishingStrategy strategy : strategies) {
            if (strategy.isAvailable()) {
                try {
                    strategy.publish(event);
                    logger.trace("Event {} published successfully using {} strategy", 
                        event.getClass().getSimpleName(), 
                        strategy.getStrategyName());
                } catch (Exception e) {
                    logger.error("Failed to publish event {} using {} strategy", 
                        event.getClass().getSimpleName(), 
                        strategy.getStrategyName(), 
                        e);
                    // Continue với các strategies khác
                }
            } else {
                logger.warn("Strategy {} is not available, skipping", strategy.getStrategyName());
            }
        }
    }

    @Override
    public boolean isAvailable() {
        // Composite strategy available nếu có ít nhất một strategy available
        return strategies.stream().anyMatch(PublishingStrategy::isAvailable);
    }

    @Override
    public String getStrategyName() {
        return "Composite[" + strategies.size() + " strategies]";
    }
}


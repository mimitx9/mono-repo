package com.example.infra.config;

import com.example.infra.adapter.event.strategy.PublishingStrategy;
import com.example.infra.adapter.event.strategy.SpringEventsPublishingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration cho Event Publishing Strategies.
 * Cho phép chọn strategy thông qua application properties.
 * 
 * Ví dụ:
 * - event.publisher.strategy=spring (mặc định)
 * - event.publisher.strategy=kafka
 * - event.publisher.strategy=rabbitmq
 * - event.publisher.strategy=composite
 */
@Configuration
public class EventPublisherStrategyConfiguration {

    /**
     * Spring Events Publishing Strategy (mặc định).
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "event.publisher.strategy", havingValue = "spring", matchIfMissing = true)
    public PublishingStrategy springEventsPublishingStrategy(ApplicationEventPublisher applicationEventPublisher) {
        return new SpringEventsPublishingStrategy(applicationEventPublisher);
    }

    // Có thể thêm các strategies khác khi cần:
    // @Bean
    // @ConditionalOnProperty(name = "event.publisher.strategy", havingValue = "kafka")
    // public PublishingStrategy kafkaPublishingStrategy() {
    //     return new KafkaPublishingStrategy("events");
    // }
}


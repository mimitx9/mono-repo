package com.example.infra.adapter.event.strategy;

import com.example.domain.example.event.UserCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cho KafkaPublishingStrategy.
 */
class KafkaPublishingStrategyTest {

    private KafkaPublishingStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new KafkaPublishingStrategy("events");
    }

    @Test
    void shouldPublishEvent() {
        // Given
        UserCreatedEvent event = new UserCreatedEvent("user-123", "John Doe", "john@example.com");

        // When
        strategy.publish(event);

        // Then
        assertEquals(1, strategy.getPublishedEvents().size());
        assertEquals(event, strategy.getPublishedEvents().get(0));
    }

    @Test
    void shouldBeAvailable() {
        // When
        boolean available = strategy.isAvailable();

        // Then
        assertTrue(available);
    }

    @Test
    void shouldReturnCorrectStrategyName() {
        // When
        String name = strategy.getStrategyName();

        // Then
        assertEquals("Kafka", name);
    }

    @Test
    void shouldPublishMultipleEvents() {
        // Given
        UserCreatedEvent event1 = new UserCreatedEvent("user-1", "John", "john@example.com");
        UserCreatedEvent event2 = new UserCreatedEvent("user-2", "Jane", "jane@example.com");

        // When
        strategy.publish(event1);
        strategy.publish(event2);

        // Then
        assertEquals(2, strategy.getPublishedEvents().size());
        assertTrue(strategy.getPublishedEvents().contains(event1));
        assertTrue(strategy.getPublishedEvents().contains(event2));
    }
}


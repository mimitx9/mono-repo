package com.example.infra.adapter.event.strategy;

import com.example.domain.example.event.UserCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho SpringEventsPublishingStrategy.
 */
@ExtendWith(MockitoExtension.class)
class SpringEventsPublishingStrategyTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private SpringEventsPublishingStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new SpringEventsPublishingStrategy(applicationEventPublisher);
    }

    @Test
    void shouldPublishEvent() {
        // Given
        UserCreatedEvent event = new UserCreatedEvent("user-123", "John Doe", "john@example.com");

        // When
        strategy.publish(event);

        // Then
        verify(applicationEventPublisher).publishEvent(event);
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
        assertEquals("SpringEvents", name);
    }
}


package com.example.infra.adapter.event.strategy;

import com.example.domain.example.event.UserCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho CompositePublishingStrategy.
 */
@ExtendWith(MockitoExtension.class)
class CompositePublishingStrategyTest {

    @Mock
    private PublishingStrategy strategy1;

    @Mock
    private PublishingStrategy strategy2;

    private CompositePublishingStrategy compositeStrategy;

    @BeforeEach
    void setUp() {
        List<PublishingStrategy> strategies = Arrays.asList(strategy1, strategy2);
        compositeStrategy = new CompositePublishingStrategy(strategies);
    }

    @Test
    void shouldPublishToAllStrategies() {
        // Given
        UserCreatedEvent event = new UserCreatedEvent("user-123", "John Doe", "john@example.com");
        when(strategy1.isAvailable()).thenReturn(true);
        when(strategy2.isAvailable()).thenReturn(true);
        when(strategy1.getStrategyName()).thenReturn("Strategy1");
        when(strategy2.getStrategyName()).thenReturn("Strategy2");

        // When
        compositeStrategy.publish(event);

        // Then
        verify(strategy1).publish(event);
        verify(strategy2).publish(event);
    }

    @Test
    void shouldSkipUnavailableStrategies() {
        // Given
        UserCreatedEvent event = new UserCreatedEvent("user-123", "John Doe", "john@example.com");
        when(strategy1.isAvailable()).thenReturn(true);
        when(strategy2.isAvailable()).thenReturn(false);
        when(strategy1.getStrategyName()).thenReturn("Strategy1");
        when(strategy2.getStrategyName()).thenReturn("Strategy2");

        // When
        compositeStrategy.publish(event);

        // Then
        verify(strategy1).publish(event);
        verify(strategy2, never()).publish(event);
    }

    @Test
    void shouldContinueWithOtherStrategiesWhenOneFails() {
        // Given
        UserCreatedEvent event = new UserCreatedEvent("user-123", "John Doe", "john@example.com");
        when(strategy1.isAvailable()).thenReturn(true);
        when(strategy2.isAvailable()).thenReturn(true);
        when(strategy1.getStrategyName()).thenReturn("Strategy1");
        when(strategy2.getStrategyName()).thenReturn("Strategy2");
        
        doThrow(new RuntimeException("Strategy1 failed")).when(strategy1).publish(event);

        // When
        compositeStrategy.publish(event);

        // Then
        verify(strategy1).publish(event);
        verify(strategy2).publish(event); // Should still be called
    }

    @Test
    void shouldBeAvailableIfAtLeastOneStrategyIsAvailable() {
        // Given
        when(strategy1.isAvailable()).thenReturn(false);
        when(strategy2.isAvailable()).thenReturn(true);

        // When
        boolean available = compositeStrategy.isAvailable();

        // Then
        assertTrue(available);
    }

    @Test
    void shouldNotBeAvailableIfAllStrategiesAreUnavailable() {
        // Given
        when(strategy1.isAvailable()).thenReturn(false);
        when(strategy2.isAvailable()).thenReturn(false);

        // When
        boolean available = compositeStrategy.isAvailable();

        // Then
        assertFalse(available);
    }
}


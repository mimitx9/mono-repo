package com.example.common.bus;

import com.example.domain.example.query.GetUserQuery;
import com.example.domain.example.query.GetUserQueryResult;
import com.example.domain.query.QueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho QueryBus.
 */
@ExtendWith(MockitoExtension.class)
class QueryBusTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private QueryHandler<GetUserQuery, GetUserQueryResult> queryHandler;

    private QueryBus queryBus;

    @BeforeEach
    void setUp() {
        queryBus = new QueryBus(applicationContext);
    }

    @Test
    void shouldSendQueryToHandlerAndReturnResult() {
        // Given
        GetUserQuery query = new GetUserQuery("user-123");
        GetUserQueryResult expectedResult = new GetUserQueryResult("user-123", "John Doe", "john@example.com");
        
        Map<String, QueryHandler> handlers = new HashMap<>();
        handlers.put("getUserQueryHandler", queryHandler);
        
        when(applicationContext.getBeansOfType(QueryHandler.class)).thenReturn(handlers);
        when(queryHandler.handle(query)).thenReturn(expectedResult);

        // When
        GetUserQueryResult result = queryBus.send(query);

        // Then
        assertNotNull(result);
        assertEquals(expectedResult, result);
        verify(queryHandler).handle(query);
    }

    @Test
    void shouldThrowExceptionWhenHandlerNotFound() {
        // Given
        GetUserQuery query = new GetUserQuery("user-123");
        
        when(applicationContext.getBeansOfType(QueryHandler.class)).thenReturn(new HashMap<>());

        // When & Then
        HandlerNotFoundException exception = assertThrows(HandlerNotFoundException.class, () -> {
            queryBus.send(query);
        });

        assertTrue(exception.getMessage().contains("No handler found for query"));
    }

    @Test
    void shouldCacheHandlerAfterFirstLookup() {
        // Given
        GetUserQuery query1 = new GetUserQuery("user-123");
        GetUserQuery query2 = new GetUserQuery("user-456");
        GetUserQueryResult result = new GetUserQueryResult("user-123", "John Doe", "john@example.com");
        
        Map<String, QueryHandler> handlers = new HashMap<>();
        handlers.put("getUserQueryHandler", queryHandler);
        
        when(applicationContext.getBeansOfType(QueryHandler.class)).thenReturn(handlers);
        when(queryHandler.handle(any(GetUserQuery.class))).thenReturn(result);

        // When
        queryBus.send(query1);
        queryBus.send(query2);

        // Then
        // ApplicationContext chỉ được gọi một lần do caching
        verify(applicationContext, times(1)).getBeansOfType(QueryHandler.class);
        verify(queryHandler, times(2)).handle(any(GetUserQuery.class));
    }
}


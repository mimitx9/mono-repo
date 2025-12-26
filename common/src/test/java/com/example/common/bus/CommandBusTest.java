package com.example.common.bus;

import com.example.domain.command.CommandHandler;
import com.example.domain.example.command.CreateUserCommand;
import com.example.domain.example.command.CreateUserCommandHandler;
import com.example.domain.spi.EventPublisher;
import com.example.domain.spi.Repository;
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
 * Test cho CommandBus.
 */
@ExtendWith(MockitoExtension.class)
class CommandBusTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Repository<com.example.domain.example.User, String> userRepository;

    @Mock
    private EventPublisher eventPublisher;

    private CommandBus commandBus;

    @BeforeEach
    void setUp() {
        commandBus = new CommandBus(applicationContext);
    }

    @Test
    void shouldSendCommandToHandler() {
        // Given
        CreateUserCommand command = new CreateUserCommand("John Doe", "john@example.com");
        
        // Create a real handler instance
        CreateUserCommandHandler realHandler = new CreateUserCommandHandler(userRepository, eventPublisher);
        
        Map<String, CommandHandler> handlers = new HashMap<>();
        handlers.put("createUserCommandHandler", realHandler);
        
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(handlers);
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        commandBus.send(command);

        // Then
        verify(applicationContext).getBeansOfType(CommandHandler.class);
        verify(userRepository).existsById("john@example.com");
    }

    @Test
    void shouldThrowExceptionWhenHandlerNotFound() {
        // Given
        CreateUserCommand command = new CreateUserCommand("John Doe", "john@example.com");
        
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(new HashMap<>());

        // When & Then
        HandlerNotFoundException exception = assertThrows(HandlerNotFoundException.class, () -> {
            commandBus.send(command);
        });

        assertTrue(exception.getMessage().contains("No handler found for command"));
    }

    @Test
    void shouldCacheHandlerAfterFirstLookup() {
        // Given
        CreateUserCommand command1 = new CreateUserCommand("John Doe", "john@example.com");
        CreateUserCommand command2 = new CreateUserCommand("Jane Doe", "jane@example.com");
        
        CreateUserCommandHandler realHandler = new CreateUserCommandHandler(userRepository, eventPublisher);
        
        Map<String, CommandHandler> handlers = new HashMap<>();
        handlers.put("createUserCommandHandler", realHandler);
        
        when(applicationContext.getBeansOfType(CommandHandler.class)).thenReturn(handlers);
        when(userRepository.existsById(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        commandBus.send(command1);
        commandBus.send(command2);

        // Then
        // ApplicationContext chỉ được gọi một lần do caching
        verify(applicationContext, times(1)).getBeansOfType(CommandHandler.class);
        verify(userRepository, times(2)).existsById(anyString());
    }
}

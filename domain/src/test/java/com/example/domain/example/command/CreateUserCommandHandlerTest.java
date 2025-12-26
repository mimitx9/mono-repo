package com.example.domain.example.command;

import com.example.domain.example.Email;
import com.example.domain.example.User;
import com.example.domain.example.event.UserCreatedEvent;
import com.example.domain.spi.EventPublisher;
import com.example.domain.spi.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test cho CreateUserCommandHandler.
 */
@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private Repository<User, String> userRepository;

    @Mock
    private EventPublisher eventPublisher;

    private CreateUserCommandHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CreateUserCommandHandler(userRepository, eventPublisher);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        String name = "John Doe";
        String email = "john@example.com";
        CreateUserCommand command = new CreateUserCommand(name, email);
        
        when(userRepository.existsById(email)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        handler.handle(command);

        // Then
        verify(userRepository).existsById(email);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User savedUser = userCaptor.getValue();
        assertNotNull(savedUser);
        assertEquals(name, savedUser.getName());
        assertEquals(email, savedUser.getEmailValue());

        // Verify event was published
        ArgumentCaptor<UserCreatedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedEvent.class);
        verify(eventPublisher).publish(eventCaptor.capture());
        
        UserCreatedEvent event = eventCaptor.getValue();
        assertNotNull(event);
        assertEquals(savedUser.getId(), event.getUserId());
        assertEquals(name, event.getUserName());
        assertEquals(email, event.getUserEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Given
        String email = "existing@example.com";
        CreateUserCommand command = new CreateUserCommand("John Doe", email);
        
        when(userRepository.existsById(email)).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(command);
        });

        assertEquals("User with email " + email + " already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(eventPublisher, never()).publish(any());
    }
}


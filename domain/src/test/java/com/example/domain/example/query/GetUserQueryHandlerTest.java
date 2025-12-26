package com.example.domain.example.query;

import com.example.domain.example.Email;
import com.example.domain.example.User;
import com.example.domain.spi.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho GetUserQueryHandler.
 */
@ExtendWith(MockitoExtension.class)
class GetUserQueryHandlerTest {

    @Mock
    private Repository<User, String> userRepository;

    private GetUserQueryHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GetUserQueryHandler(userRepository);
    }

    @Test
    void shouldReturnUserWhenFound() {
        // Given
        String userId = "user-123";
        User user = new User(userId, "John Doe", new Email("john@example.com"));
        GetUserQuery query = new GetUserQuery(userId);
        
        when(userRepository.findById(userId)).thenReturn(user);

        // When
        GetUserQueryResult result = handler.handle(query);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.id());
        assertEquals("John Doe", result.name());
        assertEquals("john@example.com", result.email());
        
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        String userId = "non-existent";
        GetUserQuery query = new GetUserQuery(userId);
        
        when(userRepository.findById(userId)).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(query);
        });

        assertEquals("User not found: " + userId, exception.getMessage());
        verify(userRepository).findById(userId);
    }
}


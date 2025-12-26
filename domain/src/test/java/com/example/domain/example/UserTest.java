package com.example.domain.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cho Domain Model User.
 */
class UserTest {

    @Test
    void shouldCreateValidUser() {
        // Given
        String id = "user-123";
        String name = "John Doe";
        Email email = new Email("john@example.com");

        // When
        User user = new User(id, name, email);

        // Then
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals("john@example.com", user.getEmailValue());
    }

    @Test
    void shouldThrowExceptionForNullId() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "John Doe", new Email("john@example.com"));
        });
    }

    @Test
    void shouldThrowExceptionForEmptyId() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "John Doe", new Email("john@example.com"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new User("   ", "John Doe", new Email("john@example.com"));
        });
    }

    @Test
    void shouldThrowExceptionForNullName() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new User("user-123", null, new Email("john@example.com"));
        });
    }

    @Test
    void shouldThrowExceptionForEmptyName() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new User("user-123", "", new Email("john@example.com"));
        });
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new User("user-123", "John Doe", null);
        });
    }

    @Test
    void shouldChangeName() {
        // Given
        User user = new User("user-123", "John Doe", new Email("john@example.com"));

        // When
        user.changeName("Jane Doe");

        // Then
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    void shouldThrowExceptionWhenChangingNameToNull() {
        // Given
        User user = new User("user-123", "John Doe", new Email("john@example.com"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            user.changeName(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenChangingNameToEmpty() {
        // Given
        User user = new User("user-123", "John Doe", new Email("john@example.com"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            user.changeName("");
        });
    }

    @Test
    void shouldReturnEmailAsValueObject() {
        // Given
        User user = new User("user-123", "John Doe", new Email("john@example.com"));

        // When
        Email email = user.getEmail();

        // Then
        assertNotNull(email);
        assertEquals("john@example.com", email.getValue());
    }
}


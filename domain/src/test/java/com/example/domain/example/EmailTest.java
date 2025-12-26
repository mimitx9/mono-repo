package com.example.domain.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cho Value Object Email.
 */
class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        // Given
        String validEmail = "test@example.com";

        // When
        Email email = new Email(validEmail);

        // Then
        assertNotNull(email);
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void shouldNormalizeEmailToLowerCase() {
        // Given
        String emailWithUpperCase = "Test@Example.COM";

        // When
        Email email = new Email(emailWithUpperCase);

        // Then
        assertEquals("test@example.com", email.getValue());
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new Email(null);
        });
    }

    @Test
    void shouldThrowExceptionForInvalidEmailFormat() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid-email");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid@");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Email("@example.com");
        });
    }

    @Test
    void shouldBeEqualForSameEmailValue() {
        // Given
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");

        // Then
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    void shouldNotBeEqualForDifferentEmailValues() {
        // Given
        Email email1 = new Email("test1@example.com");
        Email email2 = new Email("test2@example.com");

        // Then
        assertNotEquals(email1, email2);
    }
}


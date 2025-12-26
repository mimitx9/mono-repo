package com.example.domain.example;

import com.example.domain.valueobject.ValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * Ví dụ Value Object - Email.
 * Value Object là immutable và được so sánh bằng giá trị.
 */
@Getter
@EqualsAndHashCode
@ToString
public class Email implements ValueObject {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private final String value;

    public Email(String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
        this.value = value.toLowerCase();
    }
}


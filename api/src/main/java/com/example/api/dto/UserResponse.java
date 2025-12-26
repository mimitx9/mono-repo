package com.example.api.dto;

/**
 * DTO cho response user.
 */
public record UserResponse(
    String id,
    String name,
    String email
) implements BaseDto {
}


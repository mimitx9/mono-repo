package com.example.api.dto;

public record UserResponse(
    String id,
    String name,
    String email
) implements BaseDto {
}


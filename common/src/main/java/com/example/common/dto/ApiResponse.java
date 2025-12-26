package com.example.common.dto;

import java.time.Instant;

/**
 * Standard response wrapper cho API responses.
 * Được đặt trong common để tất cả các API modules có thể sử dụng.
 * 
 * @param <T> Loại data trong response
 */
public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    Instant timestamp
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data, Instant.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, Instant.now());
    }
}


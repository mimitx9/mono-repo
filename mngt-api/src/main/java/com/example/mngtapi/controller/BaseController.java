package com.example.mngtapi.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.bus.CommandBus;
import com.example.common.bus.QueryBus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Base controller với các utilities chung cho tất cả controllers trong Mngt API.
 */
public abstract class BaseController {
    
    protected final CommandBus commandBus;
    protected final QueryBus queryBus;

    protected BaseController(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    /**
     * Tạo success response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    /**
     * Tạo success response với message.
     */
    protected <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(ApiResponse.success(message, data));
    }

    /**
     * Tạo error response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(message));
    }

    /**
     * Tạo created response.
     */
    protected <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data));
    }
}


package com.example.common.bus;

/**
 * Exception được throw khi không tìm thấy handler cho command/query.
 */
public class HandlerNotFoundException extends RuntimeException {
    
    public HandlerNotFoundException(String message) {
        super(message);
    }
}


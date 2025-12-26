package com.example.domain.example.event;

import com.example.domain.event.DomainEvent;

/**
 * Ví dụ Domain Event - UserCreatedEvent.
 * Event này được publish khi một user mới được tạo.
 */
public class UserCreatedEvent extends DomainEvent {
    private final String userId;
    private final String userName;
    private final String userEmail;

    public UserCreatedEvent(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}


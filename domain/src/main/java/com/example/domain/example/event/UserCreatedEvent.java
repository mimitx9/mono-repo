package com.example.domain.example.event;

import com.example.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Ví dụ Domain Event - UserCreatedEvent.
 * Event này được publish khi một user mới được tạo.
 */
@Getter
@AllArgsConstructor
public class UserCreatedEvent extends DomainEvent {
    private final String userId;
    private final String userName;
    private final String userEmail;
}


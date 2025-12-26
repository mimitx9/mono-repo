package com.example.domain.event;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class cho tất cả các Domain Events.
 * Domain Event đại diện cho một sự kiện đã xảy ra trong domain.
 */
@Getter
public abstract class DomainEvent {
    private final UUID eventId;
    private final Instant occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = Instant.now();
    }
}


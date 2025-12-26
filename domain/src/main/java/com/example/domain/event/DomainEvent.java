package com.example.domain.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class cho tất cả các Domain Events.
 * Domain Event đại diện cho một sự kiện đã xảy ra trong domain.
 */
public abstract class DomainEvent {
    private final UUID eventId;
    private final Instant occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = Instant.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}


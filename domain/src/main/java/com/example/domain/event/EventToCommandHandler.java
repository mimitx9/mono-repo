package com.example.domain.event;

import com.example.domain.command.Command;

/**
 * Interface cho các handler có thể convert Domain Event thành Command.
 * Đây là cơ chế cho phép Domain Event "fire" đến CommandHandler.
 * 
 * Pattern này hữu ích cho:
 * - Event Sourcing
 * - Saga pattern
 * - Event-driven workflows
 * - Compensating transactions
 * 
 * @param <E> Loại Domain Event
 * @param <C> Loại Command được tạo ra từ event
 */
public interface EventToCommandHandler<E extends DomainEvent, C extends Command> {
    
    /**
     * Convert domain event thành command.
     * 
     * @param event Domain event
     * @return Command tương ứng
     */
    C toCommand(E event);
    
    /**
     * Lấy loại event mà handler này xử lý.
     * 
     * @return Class của event type
     */
    Class<E> getEventType();
    
    /**
     * Lấy loại command mà handler này tạo ra.
     * 
     * @return Class của command type
     */
    Class<C> getCommandType();
}


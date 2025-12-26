package com.example.common.bus;

import com.example.domain.command.Command;
import com.example.domain.event.DomainEvent;
import com.example.domain.event.EventToCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event-to-Command Bus implementation.
 * Bus này chịu trách nhiệm convert Domain Events thành Commands và gửi đến CommandBus.
 * 
 * Đây là cơ chế cho phép Domain Event "fire" đến CommandHandler, như mô tả trong kiến trúc.
 * 
 * Use cases:
 * - Event Sourcing: Event có thể trigger command để rebuild state
 * - Saga pattern: Event từ một service có thể trigger command ở service khác
 * - Compensating transactions: Event có thể trigger compensating command
 */
@Component
public class EventToCommandBus {
    
    private static final Logger logger = LoggerFactory.getLogger(EventToCommandBus.class);
    
    private final ApplicationContext applicationContext;
    private final CommandBus commandBus;
    private final Map<Class<? extends DomainEvent>, EventToCommandHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();

    public EventToCommandBus(ApplicationContext applicationContext, CommandBus commandBus) {
        this.applicationContext = applicationContext;
        this.commandBus = commandBus;
    }

    /**
     * Xử lý domain event bằng cách convert thành command và gửi đến CommandBus.
     * 
     * @param event Domain event cần xử lý
     */
    @SuppressWarnings("unchecked")
    public void handleEvent(DomainEvent event) {
        Class<? extends DomainEvent> eventType = event.getClass();
        
        EventToCommandHandler<?, ?> handler = handlerCache.computeIfAbsent(
            eventType,
            type -> findHandler(type)
        );
        
        if (handler == null) {
            logger.debug("No EventToCommandHandler found for event: {}", eventType.getName());
            return; // Không phải lỗi, chỉ là event này không cần convert thành command
        }
        
        try {
            // Sử dụng unchecked cast vì chúng ta đã verify handler có thể xử lý event type này
            EventToCommandHandler<DomainEvent, Command> typedHandler = 
                (EventToCommandHandler<DomainEvent, Command>) handler;
            Command command = typedHandler.toCommand(event);
            logger.debug("Converting event {} to command {}", eventType.getSimpleName(), command.getClass().getSimpleName());
            commandBus.send(command);
        } catch (Exception e) {
            logger.error("Error converting event {} to command", eventType.getName(), e);
            throw new EventToCommandException("Failed to convert event to command: " + eventType.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private EventToCommandHandler<?, ?> findHandler(Class<? extends DomainEvent> eventType) {
        // Tìm handler trong Spring context
        Map<String, EventToCommandHandler> handlers = applicationContext.getBeansOfType(EventToCommandHandler.class);
        
        for (EventToCommandHandler handler : handlers.values()) {
            if (handler.getEventType().isAssignableFrom(eventType)) {
                return handler;
            }
        }
        
        return null;
    }
    
    /**
     * Exception khi convert event sang command thất bại.
     */
    public static class EventToCommandException extends RuntimeException {
        public EventToCommandException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}


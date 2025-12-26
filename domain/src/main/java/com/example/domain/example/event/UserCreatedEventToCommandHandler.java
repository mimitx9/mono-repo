package com.example.domain.example.event;

import com.example.domain.command.Command;
import com.example.domain.event.DomainEvent;
import com.example.domain.event.EventToCommandHandler;

/**
 * Ví dụ EventToCommandHandler - UserCreatedEventToCommandHandler.
 * 
 * Handler này convert UserCreatedEvent thành một Command (ví dụ: SendWelcomeEmailCommand).
 * 
 * Đây là ví dụ về cơ chế Domain Event có thể "fire" đến CommandHandler.
 * 
 * Use cases:
 * - Khi user được tạo, có thể tự động gửi welcome email
 * - Có thể trigger các compensating actions
 * - Có thể trigger các workflows phức tạp
 * 
 * Note: Trong ví dụ này, chúng ta chỉ định nghĩa interface.
 * Implementation cụ thể sẽ được tạo trong application layer nếu cần.
 */
public class UserCreatedEventToCommandHandler implements EventToCommandHandler<UserCreatedEvent, Command> {
    
    @Override
    public Command toCommand(UserCreatedEvent event) {
        // Ví dụ: Convert UserCreatedEvent thành SendWelcomeEmailCommand
        // return new SendWelcomeEmailCommand(event.getUserId(), event.getUserEmail());
        
        // Hoặc có thể return null nếu không cần convert
        // Trong trường hợp này, chỉ là ví dụ về pattern
        return null;
    }
    
    @Override
    public Class<UserCreatedEvent> getEventType() {
        return UserCreatedEvent.class;
    }
    
    @Override
    public Class<Command> getCommandType() {
        return Command.class;
    }
}


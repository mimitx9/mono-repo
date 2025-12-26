package com.example.domain.example.command;

import com.example.domain.command.CommandHandler;
import com.example.domain.example.event.UserCreatedEvent;
import com.example.domain.example.Email;
import com.example.domain.example.User;
import com.example.domain.spi.EventPublisher;
import com.example.domain.spi.Repository;

import java.util.UUID;

/**
 * Ví dụ Command Handler - CreateUserCommandHandler.
 * Handler này xử lý CreateUserCommand và tạo User mới.
 */
/**
 * Ví dụ Command Handler - CreateUserCommandHandler.
 * Handler này xử lý CreateUserCommand và tạo User mới.
 * 
 * Note: @Component annotation sẽ được thêm bởi executable modules (api, mngt-api, etc.)
 * thông qua @ComponentScan hoặc manual bean registration.
 */
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
    
    private final Repository<User, String> userRepository;
    private final EventPublisher eventPublisher;

    public CreateUserCommandHandler(
            Repository<User, String> userRepository,
            EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(CreateUserCommand command) {
        // Validate
        if (userRepository.existsById(command.email())) {
            throw new IllegalArgumentException("User with email " + command.email() + " already exists");
        }

        // Create domain model
        Email email = new Email(command.email());
        User user = new User(UUID.randomUUID().toString(), command.name(), email);
        
        // Save
        userRepository.save(user);
        
        // Publish domain event
        eventPublisher.publish(new UserCreatedEvent(user.getId(), user.getName(), user.getEmailValue()));
    }
}


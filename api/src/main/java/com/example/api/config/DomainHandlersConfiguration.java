package com.example.api.config;

import com.example.domain.example.User;
import com.example.domain.example.command.CreateUserCommandHandler;
import com.example.domain.example.query.GetUserQueryHandler;
import com.example.domain.example.spi.UserRepository;
import com.example.domain.spi.EventPublisher;
import com.example.domain.spi.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainHandlersConfiguration {

    @Bean
    public CreateUserCommandHandler createUserCommandHandler(
            UserRepository userRepository,
            EventPublisher eventPublisher) {
        return new CreateUserCommandHandler(
            (Repository<User, String>) userRepository, 
            eventPublisher
        );
    }

    @Bean
    public GetUserQueryHandler getUserQueryHandler(UserRepository userRepository) {
        return new GetUserQueryHandler((Repository<User, String>) userRepository);
    }
}


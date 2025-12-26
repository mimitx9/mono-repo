package com.example.infra.config;

import com.example.domain.example.User;
import com.example.domain.example.spi.UserRepository;
import com.example.infra.adapter.db.UserRepositoryAdapter;
import com.example.infra.adapter.db.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration cho Infra module.
 * Đây là nơi wire các adapters vào SPI interfaces.
 * 
 * Note: EventPublisher bean được định nghĩa ở application level (ApiConfiguration, etc.)
 * để tránh conflict khi nhiều modules cùng sử dụng.
 */
@Configuration
public class InfraConfiguration {

    /**
     * Wire UserRepositoryAdapter vào UserRepository SPI.
     */
    @Bean
    public UserRepository userRepository(UserJpaRepository jpaRepository) {
        return new UserRepositoryAdapter(jpaRepository);
    }
}


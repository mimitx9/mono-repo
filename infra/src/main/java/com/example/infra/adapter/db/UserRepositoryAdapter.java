package com.example.infra.adapter.db;

import com.example.domain.example.User;
import com.example.domain.example.spi.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation cho UserRepository SPI.
 * Đây là một Adapter trong Hexagonal Architecture, implement UserRepository port.
 */
@Component
public class UserRepositoryAdapter extends DbAdapter<User, String, UserJpaRepository> 
        implements UserRepository {
    
    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        super(jpaRepository);
    }
}


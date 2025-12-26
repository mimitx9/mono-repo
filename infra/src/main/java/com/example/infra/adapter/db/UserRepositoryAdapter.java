package com.example.infra.adapter.db;

import com.example.domain.example.User;
import com.example.domain.example.spi.UserRepository;
import com.example.infra.adapter.db.entity.UserEntity;
import com.example.infra.adapter.db.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Adapter implementation cho UserRepository SPI.
 * Đây là một Adapter trong Hexagonal Architecture, implement UserRepository port.
 * 
 * Adapter này sử dụng UserMapper để convert giữa Domain Model (User) và JPA Entity (UserEntity),
 * đảm bảo Domain Model không phụ thuộc vào JPA annotations.
 */
@Component
public class UserRepositoryAdapter extends DbAdapter<User, String, UserEntity, UserJpaRepository> 
        implements UserRepository {
    
    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    protected UserEntity toEntity(User domainModel) {
        return UserMapper.toEntity(domainModel);
    }

    @Override
    protected User toDomain(UserEntity entity) {
        return UserMapper.toDomain(entity);
    }
}


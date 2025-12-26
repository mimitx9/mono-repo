package com.example.infra.adapter.db.mapper;

import com.example.domain.example.Email;
import com.example.domain.example.User;
import com.example.infra.adapter.db.entity.UserEntity;

/**
 * Mapper để convert giữa Domain Model (User) và JPA Entity (UserEntity).
 * Đây là một phần của Adapter layer, chịu trách nhiệm mapping giữa domain và infrastructure.
 */
public class UserMapper {
    
    /**
     * Convert Domain Model sang JPA Entity.
     * 
     * @param user Domain model
     * @return JPA entity
     */
    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
            user.getId(),
            user.getName(),
            user.getEmailValue()
        );
    }
    
    /**
     * Convert JPA Entity sang Domain Model.
     * 
     * @param entity JPA entity
     * @return Domain model
     */
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        Email email = new Email(entity.getEmail());
        return new User(
            entity.getId(),
            entity.getName(),
            email
        );
    }
}


package com.example.domain.example;

import com.example.domain.model.DomainModel;

/**
 * Ví dụ Domain Model - User.
 * Đây là một entity trong domain với business logic.
 * 
 * Domain Model này hoàn toàn độc lập với infrastructure (JPA, database, etc.).
 * JPA annotations và persistence logic nằm trong Infra layer (UserEntity).
 */
public class User implements DomainModel {
    
    private final String id;
    private String name;
    private final String email;

    public User(String id, String name, Email email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (email == null) {
            throw new IllegalArgumentException("User email cannot be null");
        }
        this.id = id;
        this.name = name;
        this.email = email.getValue();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return new Email(email);
    }
    
    public String getEmailValue() {
        return email;
    }

    public void changeName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = newName;
    }
}


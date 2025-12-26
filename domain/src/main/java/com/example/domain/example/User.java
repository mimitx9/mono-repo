package com.example.domain.example;

import com.example.domain.model.DomainModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Ví dụ Domain Model - User.
 * Đây là một entity trong domain với business logic.
 * 
 * Note: Trong thực tế, JPA annotations có thể được đặt trong Infra layer
 * thông qua JPA entities riêng, nhưng để đơn giản hóa ví dụ, 
 * chúng ta đặt ở đây. Có thể tách thành User (domain) và UserEntity (infra).
 */
@Entity
@Table(name = "users")
public class User implements DomainModel {
    
    @Id
    private String id;
    private String name;
    private String email;

    // JPA constructor
    protected User() {
    }

    public User(String id, String name, Email email) {
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


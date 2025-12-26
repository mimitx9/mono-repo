package com.example.infra.adapter.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA Entity cho User.
 * Đây là persistence entity trong Infra layer, tách biệt hoàn toàn với Domain Model.
 * 
 * Note: Entity này chỉ chứa JPA annotations và mapping logic.
 * Business logic nằm trong Domain Model (User).
 */
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    private String id;
    private String name;
    private String email;

    // JPA constructor
    protected UserEntity() {
    }

    public UserEntity(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


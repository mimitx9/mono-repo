package com.example.infra.adapter.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA Entity cho User.
 * Đây là persistence entity trong Infra layer, tách biệt hoàn toàn với Domain Model.
 * 
 * Note: Entity này chỉ chứa JPA annotations và mapping logic.
 * Business logic nằm trong Domain Model (User).
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    private String id;
    private String name;
    private String email;
}


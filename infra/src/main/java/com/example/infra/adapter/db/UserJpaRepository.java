package com.example.infra.adapter.db;

import com.example.domain.example.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository cho User.
 * Đây là Spring Data JPA repository interface.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
}


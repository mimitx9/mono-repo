package com.example.infra.adapter.db;

import com.example.infra.adapter.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository cho UserEntity.
 * Đây là Spring Data JPA repository interface.
 * Sử dụng UserEntity thay vì Domain Model để tách biệt hoàn toàn.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}


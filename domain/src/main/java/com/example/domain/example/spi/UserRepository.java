package com.example.domain.example.spi;

import com.example.domain.example.User;
import com.example.domain.spi.Repository;

/**
 * SPI interface cho User Repository.
 * Đây là một Port trong Hexagonal Architecture.
 */
public interface UserRepository extends Repository<User, String> {
    // Có thể thêm các methods cụ thể cho User nếu cần
    // Ví dụ: User findByEmail(String email);
}


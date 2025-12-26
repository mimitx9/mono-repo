package com.example.domain.example.query;

/**
 * Kết quả của GetUserQuery.
 */
public record GetUserQueryResult(
    String id,
    String name,
    String email
) {
}


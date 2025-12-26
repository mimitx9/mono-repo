package com.example.domain.example.query;

import com.example.domain.query.Query;

/**
 * Ví dụ Query - GetUserQuery.
 * Query đại diện cho intent để lấy thông tin user.
 */
public record GetUserQuery(String userId) implements Query<GetUserQueryResult> {
}


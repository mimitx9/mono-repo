package com.example.domain.model;

/**
 * Base interface cho tất cả các Domain Models.
 * Domain Model đại diện cho một entity trong domain với business logic.
 */
public interface DomainModel {
    /**
     * Lấy ID của domain model.
     * 
     * @return ID của model
     */
    String getId();
}


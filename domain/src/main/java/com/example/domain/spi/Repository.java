package com.example.domain.spi;

import com.example.domain.model.DomainModel;

/**
 * SPI (Service Provider Interface) cho Repository.
 * Đây là một Port trong Hexagonal Architecture, định nghĩa contract cho persistence.
 * Implementation sẽ được cung cấp bởi Infra module (Adapter).
 * 
 * @param <T> Loại Domain Model mà repository này quản lý
 * @param <ID> Loại ID của Domain Model
 */
public interface Repository<T extends DomainModel, ID> {
    /**
     * Lưu hoặc cập nhật một domain model.
     * 
     * @param entity Entity cần lưu
     * @return Entity đã được lưu
     */
    T save(T entity);

    /**
     * Tìm entity theo ID.
     * 
     * @param id ID của entity
     * @return Entity nếu tìm thấy, null nếu không
     */
    T findById(ID id);

    /**
     * Xóa entity theo ID.
     * 
     * @param id ID của entity cần xóa
     */
    void deleteById(ID id);

    /**
     * Kiểm tra entity có tồn tại không.
     * 
     * @param id ID của entity
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsById(ID id);
}


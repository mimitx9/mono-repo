package com.example.infra.adapter.db;

import com.example.domain.model.DomainModel;
import com.example.domain.spi.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Base adapter cho Database operations.
 * Đây là một Adapter trong Hexagonal Architecture, implement Repository SPI.
 * 
 * Note: Base class này giữ nguyên để tương thích với các adapter khác.
 * Các adapter cụ thể sẽ override methods để sử dụng mapper nếu cần.
 * 
 * @param <T> Loại Domain Model
 * @param <ID> Loại ID
 * @param <E> Loại JPA Entity
 * @param <JPA_REPO> Loại JPA Repository tương ứng
 */
public abstract class DbAdapter<T extends DomainModel, ID, E, JPA_REPO extends JpaRepository<E, ID>> 
        implements Repository<T, ID> {
    
    protected final JPA_REPO jpaRepository;

    protected DbAdapter(JPA_REPO jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * Convert domain model sang entity. Subclasses phải implement.
     */
    protected abstract E toEntity(T domainModel);
    
    /**
     * Convert entity sang domain model. Subclasses phải implement.
     */
    protected abstract T toDomain(E entity);

    @Override
    public T save(T entity) {
        E jpaEntity = toEntity(entity);
        E savedEntity = jpaRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public T findById(ID id) {
        Optional<E> entity = jpaRepository.findById(id);
        return entity.map(this::toDomain).orElse(null);
    }

    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return jpaRepository.existsById(id);
    }
}


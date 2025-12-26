package com.example.infra.adapter.db;

import com.example.domain.model.DomainModel;
import com.example.domain.spi.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Base adapter cho Database operations.
 * Đây là một Adapter trong Hexagonal Architecture, implement Repository SPI.
 * 
 * @param <T> Loại Domain Model
 * @param <ID> Loại ID
 * @param <JPA_REPO> Loại JPA Repository tương ứng
 */
public abstract class DbAdapter<T extends DomainModel, ID, JPA_REPO extends JpaRepository<T, ID>> 
        implements Repository<T, ID> {
    
    protected final JPA_REPO jpaRepository;

    protected DbAdapter(JPA_REPO jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public T findById(ID id) {
        Optional<T> entity = jpaRepository.findById(id);
        return entity.orElse(null);
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


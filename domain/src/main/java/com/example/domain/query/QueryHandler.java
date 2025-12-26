package com.example.domain.query;

/**
 * Base interface cho tất cả các Query Handlers.
 * Mỗi Query sẽ có một QueryHandler tương ứng để xử lý.
 * 
 * @param <T> Loại Query mà handler này xử lý
 * @param <R> Loại kết quả mà handler này trả về
 */
public interface QueryHandler<T extends Query<R>, R> {
    /**
     * Xử lý query và trả về kết quả.
     * 
     * @param query Query cần xử lý
     * @return Kết quả của query
     */
    R handle(T query);
}


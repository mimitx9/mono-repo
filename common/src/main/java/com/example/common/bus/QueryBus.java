package com.example.common.bus;

import com.example.domain.query.Query;
import com.example.domain.query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Query Bus implementation theo MediatR pattern.
 * Bus này chịu trách nhiệm routing queries đến các handlers tương ứng.
 */
@Component
public class QueryBus {
    
    private final ApplicationContext applicationContext;
    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> handlerCache = new ConcurrentHashMap<>();

    public QueryBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Gửi query đến handler tương ứng và trả về kết quả.
     * 
     * @param query Query cần xử lý
     * @param <T> Loại Query
     * @param <R> Loại kết quả
     * @return Kết quả của query
     */
    @SuppressWarnings("unchecked")
    public <T extends Query<R>, R> R send(T query) {
        Class<? extends Query<?>> queryType = (Class<? extends Query<?>>) query.getClass();
        QueryHandler<T, R> handler = (QueryHandler<T, R>) handlerCache.computeIfAbsent(
            queryType,
            type -> findHandler(type)
        );
        
        if (handler == null) {
            throw new HandlerNotFoundException("No handler found for query: " + queryType.getName());
        }
        
        return handler.handle(query);
    }

    @SuppressWarnings("unchecked")
    private QueryHandler<?, ?> findHandler(Class<? extends Query<?>> queryType) {
        // Tìm handler trong Spring context
        Map<String, QueryHandler> handlers = applicationContext.getBeansOfType(QueryHandler.class);
        
        for (QueryHandler handler : handlers.values()) {
            // Kiểm tra xem handler có thể xử lý query type này không
            if (canHandle(handler, queryType)) {
                return handler;
            }
        }
        
        return null;
    }

    private boolean canHandle(QueryHandler<?, ?> handler, Class<? extends Query<?>> queryType) {
        // Sử dụng reflection để kiểm tra generic type của handler
        java.lang.reflect.Type[] interfaces = handler.getClass().getGenericInterfaces();
        for (java.lang.reflect.Type type : interfaces) {
            if (type instanceof java.lang.reflect.ParameterizedType) {
                java.lang.reflect.ParameterizedType paramType = (java.lang.reflect.ParameterizedType) type;
                if (paramType.getRawType().equals(QueryHandler.class)) {
                    java.lang.reflect.Type[] args = paramType.getActualTypeArguments();
                    if (args.length > 0 && args[0] instanceof Class) {
                        Class<?> handlerQueryType = (Class<?>) args[0];
                        return handlerQueryType.isAssignableFrom(queryType);
                    }
                }
            }
        }
        return false;
    }
}


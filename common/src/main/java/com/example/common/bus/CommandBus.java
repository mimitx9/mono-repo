package com.example.common.bus;

import com.example.domain.command.Command;
import com.example.domain.command.CommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Command Bus implementation theo MediatR pattern.
 * Bus này chịu trách nhiệm routing commands đến các handlers tương ứng.
 */
@Component
public class CommandBus {
    
    private final ApplicationContext applicationContext;
    private final Map<Class<? extends Command>, CommandHandler<?>> handlerCache = new ConcurrentHashMap<>();

    public CommandBus(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Gửi command đến handler tương ứng.
     * 
     * @param command Command cần xử lý
     * @param <T> Loại Command
     */
    @SuppressWarnings("unchecked")
    public <T extends Command> void send(T command) {
        Class<? extends Command> commandType = command.getClass();
        CommandHandler<T> handler = (CommandHandler<T>) handlerCache.computeIfAbsent(
            commandType,
            type -> findHandler(type)
        );
        
        if (handler == null) {
            throw new HandlerNotFoundException("No handler found for command: " + commandType.getName());
        }
        
        handler.handle(command);
    }

    @SuppressWarnings("unchecked")
    private CommandHandler<?> findHandler(Class<? extends Command> commandType) {
        // Tìm handler trong Spring context
        Map<String, CommandHandler> handlers = applicationContext.getBeansOfType(CommandHandler.class);
        
        for (CommandHandler handler : handlers.values()) {
            // Kiểm tra xem handler có thể xử lý command type này không
            if (canHandle(handler, commandType)) {
                return handler;
            }
        }
        
        return null;
    }

    private boolean canHandle(CommandHandler<?> handler, Class<? extends Command> commandType) {
        // Sử dụng reflection để kiểm tra generic type của handler
        java.lang.reflect.Type[] interfaces = handler.getClass().getGenericInterfaces();
        for (java.lang.reflect.Type type : interfaces) {
            if (type instanceof java.lang.reflect.ParameterizedType) {
                java.lang.reflect.ParameterizedType paramType = (java.lang.reflect.ParameterizedType) type;
                if (paramType.getRawType().equals(CommandHandler.class)) {
                    java.lang.reflect.Type[] args = paramType.getActualTypeArguments();
                    if (args.length > 0 && args[0] instanceof Class) {
                        Class<?> handlerCommandType = (Class<?>) args[0];
                        return handlerCommandType.isAssignableFrom(commandType);
                    }
                }
            }
        }
        
        // Fallback: Kiểm tra superclass nếu không tìm thấy trong interfaces
        java.lang.reflect.Type superclass = handler.getClass().getGenericSuperclass();
        if (superclass instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.ParameterizedType paramType = (java.lang.reflect.ParameterizedType) superclass;
            if (paramType.getRawType().equals(CommandHandler.class)) {
                java.lang.reflect.Type[] args = paramType.getActualTypeArguments();
                if (args.length > 0 && args[0] instanceof Class) {
                    Class<?> handlerCommandType = (Class<?>) args[0];
                    return handlerCommandType.isAssignableFrom(commandType);
                }
            }
        }
        
        return false;
    }
}


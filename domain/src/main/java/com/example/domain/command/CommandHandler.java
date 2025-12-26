package com.example.domain.command;

/**
 * Base interface cho tất cả các Command Handlers.
 * Mỗi Command sẽ có một CommandHandler tương ứng để xử lý.
 * 
 * @param <T> Loại Command mà handler này xử lý
 */
public interface CommandHandler<T extends Command> {
    /**
     * Xử lý command và trả về kết quả.
     * 
     * @param command Command cần xử lý
     * @return Kết quả xử lý command
     */
    void handle(T command);
}


package com.example.domain.example.command;

import com.example.domain.command.Command;

/**
 * Ví dụ Command - CreateUserCommand.
 * Command đại diện cho intent để tạo một user mới.
 */
public record CreateUserCommand(
    String name,
    String email
) implements Command {
}


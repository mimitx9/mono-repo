package com.example.api.controller;

import com.example.common.dto.ApiResponse;
import com.example.api.dto.CreateUserRequest;
import com.example.api.dto.UserResponse;
import com.example.common.bus.CommandBus;
import com.example.common.bus.QueryBus;
import com.example.domain.example.command.CreateUserCommand;
import com.example.domain.example.query.GetUserQuery;
import com.example.domain.example.query.GetUserQueryResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Ví dụ Controller - UserController.
 * Controller này sử dụng CommandBus và QueryBus để giao tiếp với domain layer.
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    public UserController(CommandBus commandBus, QueryBus queryBus) {
        super(commandBus, queryBus);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            // Tạo command từ DTO
            CreateUserCommand command = new CreateUserCommand(request.name(), request.email());
            
            // Gửi command qua CommandBus
            commandBus.send(command);
            
            // Trả về response (trong thực tế có thể query lại để lấy user vừa tạo)
            return created(new UserResponse(null, request.name(), request.email()));
        } catch (IllegalArgumentException e) {
            return error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable String userId) {
        try {
            // Tạo query
            GetUserQuery query = new GetUserQuery(userId);
            
            // Gửi query qua QueryBus
            GetUserQueryResult result = queryBus.send(query);
            
            // Convert domain result sang DTO
            UserResponse response = new UserResponse(
                result.id(),
                result.name(),
                result.email()
            );
            
            return ok(response);
        } catch (IllegalArgumentException e) {
            return error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}


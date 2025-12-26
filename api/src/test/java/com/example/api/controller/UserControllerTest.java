package com.example.api.controller;

import com.example.api.dto.CreateUserRequest;
import com.example.api.dto.UserResponse;
import com.example.common.bus.CommandBus;
import com.example.common.bus.QueryBus;
import com.example.domain.example.command.CreateUserCommand;
import com.example.domain.example.query.GetUserQuery;
import com.example.domain.example.query.GetUserQueryResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho UserController.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private CommandBus commandBus;

    @Mock
    private QueryBus queryBus;

    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController(commandBus, queryBus);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John Doe", "john@example.com");

        // When
        ResponseEntity<?> response = controller.createUser(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        
        ArgumentCaptor<CreateUserCommand> commandCaptor = ArgumentCaptor.forClass(CreateUserCommand.class);
        verify(commandBus).send(commandCaptor.capture());
        
        CreateUserCommand command = commandCaptor.getValue();
        assertEquals("John Doe", command.name());
        assertEquals("john@example.com", command.email());
    }

    @Test
    void shouldReturnBadRequestWhenUserAlreadyExists() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John Doe", "existing@example.com");
        
        doThrow(new IllegalArgumentException("User with email existing@example.com already exists"))
            .when(commandBus).send(any(CreateUserCommand.class));

        // When
        ResponseEntity<?> response = controller.createUser(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldGetUserSuccessfully() {
        // Given
        String userId = "user-123";
        GetUserQueryResult queryResult = new GetUserQueryResult(userId, "John Doe", "john@example.com");
        
        when(queryBus.send(any(GetUserQuery.class))).thenReturn(queryResult);

        // When
        ResponseEntity<?> response = controller.getUser(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        ArgumentCaptor<GetUserQuery> queryCaptor = ArgumentCaptor.forClass(GetUserQuery.class);
        verify(queryBus).send(queryCaptor.capture());
        
        GetUserQuery query = queryCaptor.getValue();
        assertEquals(userId, query.userId());
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        // Given
        String userId = "non-existent";
        
        when(queryBus.send(any(GetUserQuery.class)))
            .thenThrow(new IllegalArgumentException("User not found: " + userId));

        // When
        ResponseEntity<?> response = controller.getUser(userId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}


package com.example.api.grpc;

import com.example.api.grpc.CreateUserRequest;
import com.example.api.grpc.GetUserRequest;
import com.example.api.grpc.UserResponse;
import com.example.common.bus.CommandBus;
import com.example.common.bus.QueryBus;
import com.example.domain.example.command.CreateUserCommand;
import com.example.domain.example.query.GetUserQuery;
import com.example.domain.example.query.GetUserQueryResult;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
    
    private static final Logger logger = LoggerFactory.getLogger(UserGrpcService.class);
    
    private final CommandBus commandBus;
    private final QueryBus queryBus;
    
    public UserGrpcService(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }
    
    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            logger.info("gRPC CreateUser request: name={}, email={}", request.getName(), request.getEmail());
            if (request.getName().isBlank()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Name is required")
                    .asRuntimeException());
                return;
            }
            if (request.getEmail().isBlank()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Email is required")
                    .asRuntimeException());
                return;
            }
            CreateUserCommand command = new CreateUserCommand(request.getName(), request.getEmail());
            commandBus.send(command);
            
            UserResponse response = UserResponse.newBuilder()
                .setId("")
                .setName(request.getName())
                .setEmail(request.getEmail())
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
            logger.info("gRPC CreateUser completed successfully");
        } catch (IllegalArgumentException e) {
            logger.error("gRPC CreateUser error: {}", e.getMessage(), e);
            responseObserver.onError(Status.INVALID_ARGUMENT
                .withDescription(e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("gRPC CreateUser unexpected error", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error: " + e.getMessage())
                .asRuntimeException());
        }
    }
    
    @Override
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            logger.info("gRPC GetUser request: userId={}", request.getUserId());
            if (request.getUserId().isBlank()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("User ID is required")
                    .asRuntimeException());
                return;
            }
            GetUserQuery query = new GetUserQuery(request.getUserId());
            GetUserQueryResult result = queryBus.send(query);
            UserResponse response = UserResponse.newBuilder()
                .setId(result.id() != null ? result.id() : "")
                .setName(result.name() != null ? result.name() : "")
                .setEmail(result.email() != null ? result.email() : "")
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
            logger.info("gRPC GetUser completed successfully: userId={}", request.getUserId());
        } catch (IllegalArgumentException e) {
            logger.error("gRPC GetUser error: {}", e.getMessage(), e);
            responseObserver.onError(Status.NOT_FOUND
                .withDescription(e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("gRPC GetUser unexpected error", e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Internal server error: " + e.getMessage())
                .asRuntimeException());
        }
    }
}


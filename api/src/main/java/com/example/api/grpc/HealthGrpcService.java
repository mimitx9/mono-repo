package com.example.api.grpc;

import com.example.api.grpc.HealthRequest;
import com.example.api.grpc.HealthResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class HealthGrpcService extends HealthServiceGrpc.HealthServiceImplBase {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthGrpcService.class);
    
    @Override
    public void health(HealthRequest request, StreamObserver<HealthResponse> responseObserver) {
        logger.debug("gRPC Health check request");
        
        HealthResponse response = HealthResponse.newBuilder()
            .setStatus("UP")
            .setMessage("Application is running")
            .build();
        
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        
        logger.debug("gRPC Health check completed");
    }
}


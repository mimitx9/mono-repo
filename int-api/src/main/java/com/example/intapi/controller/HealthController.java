package com.example.intapi.controller;

import com.example.common.dto.ApiResponse;
import com.example.common.bus.CommandBus;
import com.example.common.bus.QueryBus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Health check controller cho Internal API.
 */
@RestController
@RequestMapping("/api")
public class HealthController extends BaseController {

    public HealthController(CommandBus commandBus, QueryBus queryBus) {
        super(commandBus, queryBus);
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        return ok(Map.of(
            "status", "UP",
            "service", "int-api",
            "message", "Internal API is running"
        ));
    }
}



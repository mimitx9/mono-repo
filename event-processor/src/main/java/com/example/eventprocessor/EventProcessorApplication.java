package com.example.eventprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Event Processor Application.
 * Module này subscribe và xử lý các external events từ các hệ thống khác.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.eventprocessor (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.eventprocessor",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = "com.example.infra")
public class EventProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventProcessorApplication.class, args);
    }
}



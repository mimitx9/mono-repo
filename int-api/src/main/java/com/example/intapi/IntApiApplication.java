package com.example.intapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Internal API Application.
 * Module này chứa Private APIs cho internal system integration.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.intapi (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.intapi",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = "com.example.infra")
public class IntApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntApiApplication.class, args);
    }
}



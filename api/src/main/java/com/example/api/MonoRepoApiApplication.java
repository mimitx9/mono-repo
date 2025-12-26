package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class cho API module.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.api (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.api",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = {"com.example.domain", "com.example.infra"})
public class MonoRepoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonoRepoApiApplication.class, args);
    }
}

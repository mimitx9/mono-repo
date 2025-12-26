package com.example.mngtapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Management API Application.
 * Module này chứa Private APIs cho Portal/Console.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.mngtapi (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.mngtapi",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = "com.example.infra")
public class MngtApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MngtApiApplication.class, args);
    }
}



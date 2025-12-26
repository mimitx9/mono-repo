package com.example.projector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Projector Application.
 * Module này xử lý projections và view aggregations cho read models.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.projector (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.projector",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = "com.example.infra")
public class ProjectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectorApplication.class, args);
    }
}



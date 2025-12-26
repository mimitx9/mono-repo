package com.example.jobexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Job Executor Application.
 * Module này thực thi các background jobs và scheduled tasks.
 * 
 * Component scan sẽ tự động tìm các beans trong:
 * - com.example.jobexecutor (current package và sub-packages)
 * - com.example.domain (Domain module)
 * - com.example.common (Common module)
 * - com.example.infra (Infra module)
 */
@SpringBootApplication(scanBasePackages = {
    "com.example.jobexecutor",
    "com.example.domain",
    "com.example.common",
    "com.example.infra"
})
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.infra")
@EntityScan(basePackages = "com.example.infra")
public class JobExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobExecutorApplication.class, args);
    }
}



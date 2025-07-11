package com.ejemplo.spa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ejemplo.spa.repository")
@EntityScan("com.ejemplo.spa.model")
public class FirstApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstApiApplication.class, args);
    }
}

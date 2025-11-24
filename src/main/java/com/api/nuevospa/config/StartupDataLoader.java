package com.api.nuevospa.config;

import com.api.nuevospa.domain.entity.User;
import com.api.nuevospa.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class StartupDataLoader {

    @Bean
    public CommandLineRunner loadInitialData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setName("Admin SPA");
                admin.setEmail("admin@nuevospa.com");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("employee").isEmpty()) {
                User employee = new User();
                employee.setUsername("employee");
                employee.setPassword(passwordEncoder.encode("employee"));
                employee.setName("Empleado SPA");
                employee.setEmail("empleado@nuevospa.com");
                userRepository.save(employee);
            }
        };
    }
}
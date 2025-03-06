package com.provired.challenge.config;

import com.provired.challenge.entity.TaskStatus;
import com.provired.challenge.entity.User;
import com.provired.challenge.repository.TaskStatusRepository;
import com.provired.challenge.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    /**
     * CommandLineRunner para precargar datos en las tablas de usuarios y task_status.
     * Se ejecuta al iniciar la aplicación.
     *
     * @param userRepository Repositorio de usuarios.
     * @param taskStatusRepository Repositorio de estados de tarea.
     * @param passwordEncoder Utilizado para encriptar contraseñas.
     * @return Un CommandLineRunner que inserta datos predefinidos.
     */
    @Bean
    public CommandLineRunner loadInitialData(UserRepository userRepository,
                                             TaskStatusRepository taskStatusRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.count() == 0) {
                User ronny = User.builder()
                        .username("Ronny")
                        .password(passwordEncoder.encode("passwordRonny"))
                        .build();

                User joel = User.builder()
                        .username("Joel")
                        .password(passwordEncoder.encode("passwordJoel"))
                        .build();

                userRepository.save(ronny);
                userRepository.save(joel);
            }


            if (taskStatusRepository.count() == 0) {
                taskStatusRepository.save(TaskStatus.builder().name("Pendiente").build());
                taskStatusRepository.save(TaskStatus.builder().name("En Progreso").build());
                taskStatusRepository.save(TaskStatus.builder().name("Completada").build());
            }
        };
    }
}
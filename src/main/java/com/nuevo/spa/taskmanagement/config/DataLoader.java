package com.nuevo.spa.taskmanagement.config;

import com.nuevo.spa.taskmanagement.model.EstadoTarea;
import com.nuevo.spa.taskmanagement.model.Tarea;
import com.nuevo.spa.taskmanagement.model.Usuario;
import com.nuevo.spa.taskmanagement.repository.EstadoTareaRepository;
import com.nuevo.spa.taskmanagement.repository.TareaRepository;
import com.nuevo.spa.taskmanagement.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Configuration
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            EstadoTareaRepository estadoTareaRepository,
            TareaRepository tareaRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            logger.info("Iniciando carga de datos...");

            // Cargar estados de tarea si no existen
            if (!estadoTareaRepository.existsById(1L)) {
                estadoTareaRepository.saveAll(List.of(
                        new EstadoTarea(null, "Pendiente"),
                        new EstadoTarea(null, "En Progreso"),
                        new EstadoTarea(null, "Completada")
                ));
                logger.info("Estados de tarea creados.");
            }

            // Cargar usuarios si no existen
            if (!usuarioRepository.existsById(1L)) {
                Usuario usuario1 = new Usuario(null, "usuario1", passwordEncoder.encode("password1"), "usuario1@nuevo.com");
                Usuario usuario2 = new Usuario(null, "usuario2", passwordEncoder.encode("password2"), "usuario2@nuevo.com");
                Usuario usuario3 = new Usuario(null, "usuario3", passwordEncoder.encode("password3"), "usuario3@nuevo.com");

                usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3));
                logger.info("Usuarios creados.");

                // Recuperar estados desde la base de datos
                EstadoTarea estadoPendiente = estadoTareaRepository.findByNombre("Pendiente").orElse(null);
                EstadoTarea estadoEnProgreso = estadoTareaRepository.findByNombre("En Progreso").orElse(null);
                EstadoTarea estadoCompletada = estadoTareaRepository.findByNombre("Completada").orElse(null);

                // Cargar tareas si no existen
                if (!tareaRepository.existsById(1L)) {
                    tareaRepository.saveAll(List.of(
                            // Usuario 1
                            new Tarea(null, "Tarea 1 de Usuario 1", "Descripción de la tarea 1", usuario1, estadoPendiente),
                            new Tarea(null, "Tarea 2 de Usuario 1", "Descripción de la tarea 2", usuario1, estadoEnProgreso),
                            new Tarea(null, "Tarea 3 de Usuario 1", "Descripción de la tarea 3", usuario1, estadoCompletada),

                            // Usuario 2
                            new Tarea(null, "Tarea 1 de Usuario 2", "Descripción de la tarea 1", usuario2, estadoPendiente),
                            new Tarea(null, "Tarea 2 de Usuario 2", "Descripción de la tarea 2", usuario2, estadoEnProgreso),
                            new Tarea(null, "Tarea 3 de Usuario 2", "Descripción de la tarea 3", usuario2, estadoCompletada),

                            // Usuario 3
                            new Tarea(null, "Tarea 1 de Usuario 3", "Descripción de la tarea 1", usuario3, estadoPendiente),
                            new Tarea(null, "Tarea 2 de Usuario 3", "Descripción de la tarea 2", usuario3, estadoEnProgreso),
                            new Tarea(null, "Tarea 3 de Usuario 3", "Descripción de la tarea 3", usuario3, estadoCompletada)
                    ));
                    logger.info("Tareas creadas.");
                }
            }

            logger.info("Carga de datos completada.");
        };
    }
}

package com.dio.task_manager.application.service;

import com.dio.task_manager.application.dto.TaskDTO;
import com.dio.task_manager.application.dto.TaskRequest;
import com.dio.task_manager.application.mapper.TaskMapper;
import com.dio.task_manager.domain.*;
import com.dio.task_manager.domain.spec.TaskSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskStatusRepository = taskStatusRepository;
    }


    public ResponseEntity<TaskDTO> createTask(TaskRequest request, String authenticatedUsername) {
        LOGGER.info("createTask|in. Usuario autenticado: {}", authenticatedUsername);

        return (request.userId() != null
                ? userRepository.findById(request.userId())
                : userRepository.findByUsername(authenticatedUsername))
                .map(user -> {
                    TaskStatus status = taskStatusRepository.findById(request.statusId())
                            .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado: " + request.statusId()));

                    Task task = new Task();
                    task.setTitle(request.title());
                    task.setDescription(request.description());
                    task.setDueDate(request.dueDate());
                    task.setStatus(status);
                    task.setUser(user);

                    Task saved = taskRepository.save(task);
                    LOGGER.info("createTask|out. Tarea creada con ID: {}", saved.getId());

                    return ResponseEntity
                            .created(URI.create("/tasks/" + saved.getId()))
                            .body(TaskMapper.toDTO(saved));
                }).orElseGet(() -> {
                    LOGGER.warn("createTask|Usuario no encontrado (id o username)");
                    return ResponseEntity.notFound().build();
                });

    }


    public ResponseEntity<TaskDTO> updateTask(Long id, TaskRequest request, String username) {
        LOGGER.info("updateTask|in. ID: {}, Usuario: {}", id, username);

        return taskRepository.findById(id).filter(task -> {
            boolean authorized = task.getUser().getUsername().equals(username);
            if (!authorized) {
                LOGGER.warn("updateTask|Usuario no autorizado para tarea ID: {}", id);
            }
            return authorized;
        }).map(task -> {
            task.setTitle(request.title());
            task.setDescription(request.description());
            task.setDueDate(request.dueDate());
            task.setStatus(taskStatusRepository.findById(request.statusId()).orElseThrow());

            Task updated = taskRepository.save(task);
            LOGGER.info("updateTask|out. Tarea actualizada ID: {}", updated.getId());
            return ResponseEntity.ok(TaskMapper.toDTO(updated));
        }).orElseGet(() -> {
            LOGGER.warn("updateTask|Tarea no encontrada o acceso denegado ID: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    public ResponseEntity<Void> deleteTask(Long id, String username) {
        LOGGER.info("deleteTask|in. ID: {}, Usuario: {}", id, username);

        return taskRepository.findById(id)
                .map(task -> {
                    if (!task.getUser().getUsername().equals(username)) {
                        LOGGER.warn("deleteTask|Usuario no autorizado para eliminar tarea ID: {}", id);
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).<Void>build();
                    }

                    taskRepository.delete(task);
                    LOGGER.info("deleteTask|out. Tarea eliminada ID: {}", id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElseGet(() -> {
                    LOGGER.warn("deleteTask|Tarea no encontrada ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    public Page<TaskDTO> getTasks(String username, Pageable pageable) {
        LOGGER.info("getTasks|in. Usuario: {}, pageable: {}", username, pageable);

        Page<Task> page = taskRepository.findByUserUsername(username, pageable);
        Page<TaskDTO> dtoPage = page.map(TaskMapper::toDTO);

        LOGGER.info("getTasks|out. Tareas encontradas: {}", dtoPage.getTotalElements());
        return dtoPage;
    }

    public Page<TaskDTO> getTasks(String username, String title, Long statusId, LocalDate dueBefore, Pageable pageable) {
        LOGGER.info("getTasks|in. Usuario: {}, title: {}, statusId: {}, dueBefore: {}, pageable: {}",
                username, title, statusId, dueBefore, pageable);

        Specification<Task> spec = TaskSpecifications.hasUsername(username);

        if (title != null && !title.isBlank()) {
            spec = spec.and(TaskSpecifications.hasTitleLike(title));
        }

        if (statusId != null) {
            spec = spec.and(TaskSpecifications.hasStatusId(statusId));
        }

        if (dueBefore != null) {
            spec = spec.and(TaskSpecifications.dueBefore(dueBefore));
        }

        Page<TaskDTO> result = taskRepository.findAll(spec, pageable).map(TaskMapper::toDTO);

        LOGGER.info("getTasks|out. Tareas encontradas: {} para usuario: {}", result.getTotalElements(), username);
        return result;
    }


}
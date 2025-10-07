package com.nuevo.taskmanager.services;


import com.nuevo.taskmanager.entities.TaskEntity;
import com.nuevo.taskmanager.repositories.StatusRepository;
import com.nuevo.taskmanager.repositories.TaskRepository;
import com.nuevo.taskmanager.entities.StatusEntity;
import com.nuevo.taskmanager.model.Task;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService {

    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;

    public List<Task> getAllTasks() {
        log.info("Listando todas las tareas");
        List<Task> result = taskRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Se encontraron {} tareas", result.size());
        return result;
    }

    public Task getTaskById(Long id) {
        log.info("Buscando tarea con id={}", id);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tarea con id={} no encontrada", id);
                    return new EntityNotFoundException("Task not found");
                });
        return toDto(task);
    }

    public Task createTask(Task dto) {
        log.info("Creando nueva tarea: {}", dto);
        TaskEntity entity = toEntity(dto);

        if (entity.getStatus() == null) {
            log.debug("No se especificó estado, asignando estado por defecto 'PENDING'");
            StatusEntity defaultStatus = statusRepository.findByName("PENDING")
                    .orElseThrow(() -> {
                        log.error("Estado por defecto 'PENDING' no encontrado");
                        return new EntityNotFoundException("Default status not found");
                    });
            entity.setStatus(defaultStatus);
        }

        TaskEntity saved = taskRepository.save(entity);
        log.info("Tarea creada exitosamente con id={}", saved.getId());
        return toDto(saved);
    }

    public Task updateTask(Long id, Task dto) {
        log.info("Actualizando tarea con id={} con datos {}", id, dto);
        TaskEntity existing = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró la tarea con id={} para actualizar", id);
                    return new EntityNotFoundException("Task not found");
                });

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());

        if (dto.getStatusId() != null) {
            StatusEntity newStatus = statusRepository.findById(dto.getStatusId())
                    .orElseThrow(() -> {
                        log.warn("Estado con id={} no encontrado", dto.getStatusId());
                        return new EntityNotFoundException("Status not found");
                    });
            existing.setStatus(newStatus);
        }

        TaskEntity updated = taskRepository.save(existing);
        log.info("Tarea con id={} actualizada correctamente", id);
        return toDto(updated);
    }

    public void deleteTask(Long id) {
        log.info("Eliminando tarea con id={}", id);
        if (!taskRepository.existsById(id)) {
            log.warn("Intento de eliminar tarea inexistente id={}", id);
            throw new EntityNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
        log.info("Tarea con id={} eliminada", id);
    }

    private Task toDto(TaskEntity entity) {
        Task dto = new Task();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatusId(entity.getStatus() != null ? entity.getStatus().getId() : null);
        return dto;
    }

    private TaskEntity toEntity(Task dto) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        if (dto.getStatusId() != null) {
            StatusEntity status = statusRepository.findById(dto.getStatusId())
                    .orElseThrow(() -> {
                        log.warn("Estado con id={} no encontrado al mapear DTO a entidad", dto.getStatusId());
                        return new EntityNotFoundException("Status not found");
                    });
            entity.setStatus(status);
        }
        return entity;
    }
}

package com.provired.challenge.service;

import com.provired.challenge.dto.TaskDTO;
import com.provired.challenge.entity.Task;
import com.provired.challenge.exception.ResourceNotFoundException;
import com.provired.challenge.repository.TaskRepository;
import com.provired.challenge.repository.TaskStatusRepository;
import com.provired.challenge.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Transactional
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));
    }

    @Transactional
    public List<TaskDTO> getTasksByKeyword(String keyword) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Map<String, List<TaskDTO>> groupTasksByStatus() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.groupingBy(taskDTO -> switch (taskDTO.getStatusId().intValue()) {
                    case 1 -> "Pendiente";
                    case 2 -> "En Progreso";
                    case 3 -> "Completada";
                    default -> "Desconocido";
                }));
    }

    @Transactional
    public TaskDTO createTask(TaskDTO dto) {
        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.getUserId()));

        var status = taskStatusRepository.findById(dto.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado con ID: " + dto.getStatusId()));

        var task = new Task(dto.getCreatedAt(), dto.getDescription(), user, status);
        return convertToDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO dto) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));

        task.setDescription(dto.getDescription());

        userRepository.findById(dto.getUserId()).ifPresent(task::setUser);
        taskStatusRepository.findById(dto.getStatusId()).ifPresent(task::setStatus);

        return convertToDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada con ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getCreatedAt(),
                task.getDescription(),
                task.getUser().getId(),
                task.getStatus().getId()
        );
    }
}
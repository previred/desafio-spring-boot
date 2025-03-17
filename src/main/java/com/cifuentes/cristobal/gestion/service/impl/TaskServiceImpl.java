package com.cifuentes.cristobal.gestion.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cifuentes.cristobal.gestion.model.dto.TaskCreateRequest;
import com.cifuentes.cristobal.gestion.model.dto.TaskResponse;
import com.cifuentes.cristobal.gestion.model.dto.TaskStatusHistoryResponse;
import com.cifuentes.cristobal.gestion.model.dto.TaskUpdateRequest;
import com.cifuentes.cristobal.gestion.model.entity.Status;
import com.cifuentes.cristobal.gestion.model.entity.Task;
import com.cifuentes.cristobal.gestion.model.entity.TaskStatusHistory;
import com.cifuentes.cristobal.gestion.model.entity.User;
import com.cifuentes.cristobal.gestion.repository.StatusRepository;
import com.cifuentes.cristobal.gestion.repository.TaskRepository;
import com.cifuentes.cristobal.gestion.repository.TaskStatusHistoryRepository;
import com.cifuentes.cristobal.gestion.repository.UserRepository;
import com.cifuentes.cristobal.gestion.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository  userRepository;
    private final StatusRepository statusRepository;
    private final TaskStatusHistoryRepository taskStatusHistoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
    		UserRepository userRepository, StatusRepository statusRepository,
    		 TaskStatusHistoryRepository taskStatusHistoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.taskStatusHistoryRepository = taskStatusHistoryRepository;
    }

    @Override
    public TaskResponse createTask(TaskCreateRequest request, Long userId) {
        User user = getUserById(userId);

        Status pendingStatus = statusRepository.findByName("PENDING")
                .orElseThrow(() -> new RuntimeException("Default status not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUser(user);
        task.setStatus(pendingStatus);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setDeleted(false);

        Task savedTask = taskRepository.save(task);

        saveStatusHistory(savedTask, pendingStatus);

        return mapToResponse(savedTask);
    }

    @Override
    public List<TaskResponse> getTasks(Long userId) {
        User user = getUserById(userId);
        List<Task> tasks = Optional.ofNullable(taskRepository.findByUser(user))
                .orElse(Collections.emptyList())
                .stream()
                .filter(task -> !task.isDeleted())
                .collect(Collectors.toList());
        return tasks.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (task.isDeleted()) {
            throw new RuntimeException("Task not found"); 
        }
        validateOwnership(task, userId);
        return mapToResponse(task);
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwnership(task, userId);

        task.setDeleted(true);
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }
    
    @Override
    public TaskResponse updateTask(Long taskId, TaskUpdateRequest request, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwnership(task, userId);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setUpdatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }
    
    @Override
    public TaskResponse updateTaskStatus(Long taskId, String statusName, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        validateOwnership(task, userId);

        Status newStatus = statusRepository.findByName(statusName)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        if (!task.getStatus().getName().equals(newStatus.getName())) {
            task.setStatus(newStatus);
            saveStatusHistory(task, newStatus);
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
        }

        return mapToResponse(task);
    }
    
    private void saveStatusHistory(Task task, Status status) {
        TaskStatusHistory history = new TaskStatusHistory();
        history.setTask(task);
        history.setStatus(status);
        history.setChangedAt(LocalDateTime.now());
        taskStatusHistoryRepository.save(history);
    }
    
    private void validateOwnership(Task task, Long id) {
        if (!task.getUser().getId().equals(id)) {
            throw new RuntimeException("Forbidden: You don't own this task");
        }
    }
    
    private TaskResponse mapToResponse(Task task) {
        List<TaskStatusHistory> historyList = taskStatusHistoryRepository.findByTask(task);
        List<TaskStatusHistoryResponse> historyResponses = historyList.stream()
                .map(h -> new TaskStatusHistoryResponse(h.getStatus().getName(), h.getChangedAt()))
                .collect(Collectors.toList());

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getName(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                historyResponses
        );
    }
    
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
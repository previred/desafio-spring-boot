package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.TaskStatus;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.TaskNotFoundException;
import com.example.taskmanagement.model.TaskRequest;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.TaskStatusRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + taskRequest.getUserId()));
        task.setUser(user);

        TaskStatus status = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Status not found: " + taskRequest.getStatusId()));
        task.setStatus(status);

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Id de tarea no encontrado: " + id));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + taskRequest.getUserId()));
        task.setUser(user);

        TaskStatus status = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Status not found: " + taskRequest.getStatusId()));
        task.setStatus(status);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException("Id de tarea no encontrado: " + id);
        }
        taskRepository.deleteById(id);
    }
}
package com.nuevospa.tasksmanagement.services.impl;

import com.nuevospa.tasksmanagement.entities.Task;
import com.nuevospa.tasksmanagement.entities.TaskStatus;
import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.exceptions.ResourceNotFoundException;
import com.nuevospa.tasksmanagement.mappers.TaskMapper;
import com.nuevospa.tasksmanagement.model.TaskRequest;
import com.nuevospa.tasksmanagement.model.TaskResponse;
import com.nuevospa.tasksmanagement.repositories.TaskRepository;
import com.nuevospa.tasksmanagement.repositories.TaskStatusRepository;
import com.nuevospa.tasksmanagement.repositories.UserRepository;
import com.nuevospa.tasksmanagement.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing tasks.
 * Implements business logic for CRUD operations on tasks.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final UserRepository userRepository;

    /**
     * Retrieves all tasks from the database.
     * @return A list of TaskResponse DTOs.
     */
    @Override // Implementing method from TaskService
    public List<TaskResponse> getAllTasks() {
        log.debug("Fetching all tasks.");
        return taskRepository.findAll().stream()
                .map(TaskMapper::taskToTaskResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a task by its ID.
     * @param id The ID of the task.
     * @return TaskResponse DTO.
     * @throws ResourceNotFoundException if the task is not found.
     */
    @Override // Implementing method from TaskService
    public TaskResponse getTaskById(Long id) {
        log.debug("Fetching task with ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return TaskMapper.taskToTaskResponse(task);
    }

    /**
     * Creates a new task.
     * @param taskRequest DTO containing details for the new task.
     * @return TaskResponse DTO of the created task.
     * @throws ResourceNotFoundException if the status or user IDs are not found.
     */
    @Override // Implementing method from TaskService
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        log.info("Creating new task: {}", taskRequest.getTitle());

        TaskStatus status = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus", "id", taskRequest.getStatusId()));
        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", taskRequest.getUserId()));

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(status)
                .user(user)
                .dueDate(taskRequest.getDueDate())
                .build();

        Task savedTask = taskRepository.save(task);
        log.info("Task created with ID: {}", savedTask.getId());
        return TaskMapper.taskToTaskResponse(savedTask);
    }

    /**
     * Updates an existing task.
     * @param id The ID of the task to update.
     * @param taskRequest DTO containing updated task details.
     * @return TaskResponse DTO of the updated task.
     * @throws ResourceNotFoundException if the task, status, or user IDs are not found.
     */
    @Override // Implementing method from TaskService
    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        log.info("Updating task with ID: {}", id);
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        TaskStatus status = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus", "id", taskRequest.getStatusId()));
        User user = userRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", taskRequest.getUserId()));

        Task updatedTask = taskRepository.save(TaskMapper.convertToTask(taskRequest, existingTask, status, user));
        log.info("Task updated with ID: {}", updatedTask.getId());
        return TaskMapper.taskToTaskResponse(updatedTask);
    }

    /**
     * Deletes a task by its ID.
     * @param id The ID of the task to delete.
     * @throws ResourceNotFoundException if the task is not found.
     */
    @Override // Implementing method from TaskService
    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
        log.info("Task ID {} deleted successfully.", id);
    }

    /**
     * Get the tasks related to a user
     * @param userId The ID of the user related a task.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @Override // Implementing method from TaskService
    @Transactional
    public List<TaskResponse> getTasksByUserId(Long userId) {
        log.info("Attempting to retrieve tasks for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Task> tasks = taskRepository.findByUser(user);

        List<TaskResponse> taskResponses = tasks.stream()
                .map(TaskMapper::taskToTaskResponse)
                .collect(Collectors.toList());

        log.info("Retrieved {} tasks for user ID: {}", taskResponses.size(), userId);
        return taskResponses;
    }
}

package com.nuevospa.tasksmanagement.controllers;

import com.nuevospa.tasksmanagement.api.TasksApi;
import com.nuevospa.tasksmanagement.model.TaskRequest;
import com.nuevospa.tasksmanagement.model.TaskResponse;
import com.nuevospa.tasksmanagement.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing tasks.
 * Provides CRUD operations for tasks.
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    /**
     * Retrieves all tasks.
     *
     * @return ResponseEntity with a list of TaskResponse DTOs.
     */
    @Override
    public ResponseEntity<List<TaskResponse>> _getAllTasks() {
        log.info("Fetching all tasks.");
        List<TaskResponse> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return ResponseEntity with the TaskResponse DTO.
     */
    @Override
    public ResponseEntity<TaskResponse> _getTaskById(@PathVariable Long id) {
        log.info("Fetching task with ID: {}", id);
        TaskResponse task = taskService.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    /**
     * Creates a new task.
     *
     * @param taskRequest DTO containing task details.
     * @return ResponseEntity with the created TaskResponse DTO.
     */
    @Override
    public ResponseEntity<TaskResponse> _createTask(@Valid @RequestBody TaskRequest taskRequest) {
        log.info("Creating new task with title: {}", taskRequest.getTitle());
        TaskResponse createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    /**
     * Updates an existing task.
     *
     * @param id The ID of the task to update.
     * @param taskRequest DTO containing updated task details.
     * @return ResponseEntity with the updated TaskResponse DTO.
     */
    @Override
    public ResponseEntity<TaskResponse> _updateTask(@PathVariable Long id,
                                                    @Valid @RequestBody TaskRequest taskRequest) {
        log.info("Updating task with ID: {}", id);
        TaskResponse updatedTask = taskService.updateTask(id, taskRequest);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id The ID of the task to delete.
     * @return ResponseEntity indicating success.
     */
    @Override
    public ResponseEntity<Void> _deleteTask(@PathVariable Long id) {
        log.info("Deleting task with ID: {}", id);
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Override
    public ResponseEntity<List<TaskResponse>> _getTasksByUserId(@PathVariable Long userId) {
        log.info("Received request to get tasks for user ID: {}", userId);
        List<TaskResponse> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}

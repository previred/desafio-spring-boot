package com.nuevospa.tasksmanagement.services;

import com.nuevospa.tasksmanagement.model.TaskRequest;
import com.nuevospa.tasksmanagement.model.TaskResponse;

import java.util.List;

/**
 * Interface for the task management service.
 * Defines the contract for CRUD operations on tasks.
 */
public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(Long id);
    TaskResponse createTask(TaskRequest taskRequest);
    TaskResponse updateTask(Long id, TaskRequest taskRequest);
    void deleteTask(Long id);
    List<TaskResponse> getTasksByUserId(Long userId);
}

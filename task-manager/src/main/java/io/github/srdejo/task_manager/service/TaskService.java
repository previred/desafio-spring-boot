package io.github.srdejo.task_manager.service;


import io.github.srdejo.task_manager.model.TaskRequest;
import io.github.srdejo.task_manager.model.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse create(TaskRequest request);
    List<TaskResponse> findAll();
    TaskResponse findById(Long id);
    TaskResponse update(Long id, TaskRequest request);
    void delete(Long id);
}

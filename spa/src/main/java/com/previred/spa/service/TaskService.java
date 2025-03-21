package com.previred.spa.service;

import com.previred.spa.dto.TaskDTO;
import com.previred.spa.dto.TaskRequestDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTasksForUser(String username);

    TaskDTO createTaskForUser(TaskRequestDTO taskRequestDTO, String username);

    TaskDTO updateTaskForUser(Long taskId, TaskRequestDTO taskRequestDTO, String username);

    void deleteTaskForUser(Long taskId, String username);
}

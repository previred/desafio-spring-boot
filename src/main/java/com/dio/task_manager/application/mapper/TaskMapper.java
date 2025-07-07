package com.dio.task_manager.application.mapper;


import com.dio.task_manager.application.dto.TaskDTO;
import com.dio.task_manager.domain.Task;

public class TaskMapper {
    private TaskMapper(){}

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus() != null ? task.getStatus().getName() : null,
                task.getUser() != null ? task.getUser().getUsername() : null
        );
    }
}
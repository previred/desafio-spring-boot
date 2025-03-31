package com.hfsolutions.challenge.task_management.task.repository;

import org.springframework.stereotype.Component;

import com.hfsolutions.challenge.task_management.openapi.model.Task;
import com.hfsolutions.challenge.task_management.openapi.model.TaskProperties;
import com.hfsolutions.challenge.task_management.openapi.model.TaskStatus;

@Component
public class TaskMapper {

    public TaskEntity toEntity(TaskProperties taskDto) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        return taskEntity;
    }

    public Task toDto(TaskEntity taskEntity) {
        Task taskDto = new Task();
        taskDto.setId(taskEntity.getId());
        taskDto.setTitle(taskEntity.getTitle());
        taskDto.setDescription(taskEntity.getDescription());
        taskDto.setStatus(new TaskStatus(taskEntity.getStatus().getId(), taskEntity.getStatus().getName()));
        return taskDto;
    }

}

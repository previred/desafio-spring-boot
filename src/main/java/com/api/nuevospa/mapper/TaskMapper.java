package com.api.nuevospa.mapper;


import com.api.nuevospa.domain.entity.TaskStatus;
import com.api.nuevospa.domain.entity.Task;
import com.api.nuevospa.domain.entity.User;
import com.api.nuevospa.model.TaskRequest;
import com.api.nuevospa.model.TaskResponse;


public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus().getDescription());
        taskResponse.setAssignedTo(task.getAssignedTo().getName());
        return taskResponse;
    }

    public static Task toEntity(TaskRequest taskRequest, TaskStatus taskStatus, User user) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskStatus);
        task.setAssignedTo(user);
        return task;
    }
}

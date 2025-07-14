package com.nuevospa.tasksmanagement.mappers;

import com.nuevospa.tasksmanagement.entities.Task;
import com.nuevospa.tasksmanagement.entities.TaskStatus;
import com.nuevospa.tasksmanagement.entities.User;
import com.nuevospa.tasksmanagement.model.TaskRequest;
import com.nuevospa.tasksmanagement.model.TaskResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class TaskMapper {


    /**
     * Converts a Task entity to a TaskResponse DTO.
     * This method handles the mapping logic.
     * @param task The Task entity to convert.
     * @return TaskResponse DTO.
     */
    public static TaskResponse taskToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getCode(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().getCode(),
                task.getUser().getUsername(),
                task.getDueDate(),
                task.getDateCreation().atOffset(ZoneOffset.UTC),
                task.getLastModified().atOffset(ZoneOffset.UTC)
        );
    }

    public static Task convertToTask(TaskRequest taskRequest, Task existingTask, TaskStatus status, User user) {
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(status);
        existingTask.setUser(user);
        existingTask.setDueDate(taskRequest.getDueDate());
        return existingTask;
    }
}

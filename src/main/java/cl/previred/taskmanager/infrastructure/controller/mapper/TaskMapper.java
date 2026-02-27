package cl.previred.taskmanager.infrastructure.controller.mapper;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.model.TaskStatus;
import cl.previred.taskmanager.infrastructure.controller.dto.TaskResponse;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.id(),
                task.title(),
                task.description(),
                task.user().id(),
                task.user().username(),
                task.status().name(),
                task.createdAt()
        );
    }

    public static TaskStatus toStatus(String status) {
        if (status == null) return null;

        try {
            return TaskStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + status);
        }
    }

}

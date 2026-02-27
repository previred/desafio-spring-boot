package cl.previred.taskmanager.infrastructure.persistence.mapper;

import cl.previred.taskmanager.domain.model.TaskStatus;
import cl.previred.taskmanager.infrastructure.persistence.entity.TaskStatusEntity;

public final class TaskStatusMapper {

    private TaskStatusMapper() {}

    public static TaskStatus toDomain(TaskStatusEntity entity) {
        if (entity == null) return null;

        return TaskStatus.valueOf(entity.getName());
    }
}
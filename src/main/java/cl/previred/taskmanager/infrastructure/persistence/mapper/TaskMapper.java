package cl.previred.taskmanager.infrastructure.persistence.mapper;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.infrastructure.persistence.entity.TaskEntity;
import cl.previred.taskmanager.infrastructure.persistence.entity.TaskStatusEntity;
import cl.previred.taskmanager.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public final class TaskMapper {

    private TaskMapper() {}

    public static Task toDomain(TaskEntity entity) {
        if (entity == null) return null;

        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                UserMapper.toDomain(entity.getUser()),
                TaskStatusMapper.toDomain(entity.getStatus()),
                entity.getCreatedAt()
        );
    }

    public static TaskEntity toEntity(Task domain,
                                      TaskStatusEntity statusEntity,
                                      UserEntity userEntity) {
        if (domain == null) return null;

        return new TaskEntity(
                domain.id(),
                domain.title(),
                domain.description(),
                statusEntity,
                userEntity,
                domain.createdAt()
        );

    }

    /**
     * Convierte lista de entidades a lista de dominio
     */
    public static List<Task> toDomainList(List<TaskEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }

        return entities.stream()
                .map(TaskMapper::toDomain)
                .toList();
    }

}

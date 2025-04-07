package cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper;

import cl.previred.desafio.taskmanager.domain.mapper.TaskMapper;
import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.TaskStatus;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskJpaMapper implements TaskMapper<BasicTask, TaskJpaEntity> {

    @Override
    public TaskJpaEntity toEntity(BasicTask task) {
        if (task == null) return null;

        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .createdBy(task.getCreatedBy())
                .created(task.getCreated())
                .lastUpdate(task.getLastUpdate())
                .build();
    }

    @Override
    public BasicTask toDomain(TaskJpaEntity entity) {
        if (entity == null) return null;

        return BasicTask.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(TaskStatus.getByValue(entity.getStatus()))
                .createdBy(entity.getCreatedBy())
                .created(entity.getCreated())
                .lastUpdate(entity.getLastUpdate())
                .build();
    }
}

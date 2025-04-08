package cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper;

import cl.previred.desafio.taskmanager.domain.mapper.TaskMapper;
import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.TaskStatus;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskStatusJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.UserJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskStatusJpaRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskJpaMapper implements TaskMapper<BasicTask, TaskJpaEntity> {

    private final UserJpaRepository userRepository;
    private final TaskStatusJpaRepository statusRepository;

    @Override
    public TaskJpaEntity toEntity(BasicTask task) {
        if (task == null) return null;

        UserJpaEntity user = userRepository.findByUsername(task.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        TaskStatusJpaEntity status = statusRepository.findByName(task.getStatus().toString())
                .orElseThrow(() -> new RuntimeException("Estado no existe"));

        return TaskJpaEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(status)
                .user(user)
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
                .status(TaskStatus.getByValue(entity.getStatus().getName()))
                .createdBy(entity.getUser().getUsername())
                .created(entity.getCreated())
                .lastUpdate(entity.getLastUpdate())
                .build();
    }
}

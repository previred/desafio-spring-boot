package cl.previred.desafio.taskmanager.infrastructure.adapters.persistence;

import cl.previred.desafio.taskmanager.domain.mapper.TaskMapper;
import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.CreateTaskRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper.TaskJpaMapper;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreateTaskJpaRepository implements CreateTaskRepository {

    private final TaskJpaRepository jpaRepository;
    private final TaskJpaMapper taskMapper;

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity = taskMapper.toEntity((BasicTask)task);
        TaskJpaEntity savedEntity = jpaRepository.save(entity);
        return taskMapper.toDomain(savedEntity);
    }
}

package cl.previred.desafio.taskmanager.infrastructure.adapters.persistence;

import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.UpdateTaskRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper.TaskJpaMapper;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UpdateTaskJpaRepository implements UpdateTaskRepository {

    private final TaskJpaRepository jpaRepository;
    private final TaskJpaMapper taskMapper;

    @Override
    public Task update(String id, Task task) {

        Optional<TaskJpaEntity> taskJpa = jpaRepository.findById(id);

        if (taskJpa.isEmpty()) {
            return task;
        }

        BasicTask foundTask = taskMapper.toDomain(taskJpa.get());

        modifyValues(foundTask, (BasicTask) task);

        TaskJpaEntity entity = taskMapper.toEntity(foundTask);
        TaskJpaEntity updatedEntity = jpaRepository.save(entity);
        return taskMapper.toDomain(updatedEntity);
    }

    private void modifyValues(BasicTask oldTask, BasicTask newTask) {
        if (newTask == null || oldTask == null) {
            return;
        }

        if (newTask.getTitle() != null) {
            oldTask.setTitle(newTask.getTitle());
        }

        if (newTask.getDescription() != null) {
            oldTask.setDescription(newTask.getDescription());
        }

        if (newTask.getStatus() != null) {
            oldTask.setStatus(newTask.getStatus());
        }

    }
}

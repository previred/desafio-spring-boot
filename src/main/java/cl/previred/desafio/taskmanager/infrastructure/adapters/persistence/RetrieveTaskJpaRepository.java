package cl.previred.desafio.taskmanager.infrastructure.adapters.persistence;

import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.RetrieveTaskRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper.TaskJpaMapper;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RetrieveTaskJpaRepository implements RetrieveTaskRepository {

    private final TaskJpaRepository repository;
    private final TaskJpaMapper mapper;
    @Override
    public Task retrieve(String id) {

        Optional<TaskJpaEntity> taskJpa = repository.findById(id);

        if(taskJpa.isEmpty()) {
            return null;
        }

        TaskJpaEntity foundTask = taskJpa.get();

        return mapper.toDomain(foundTask);
    }
}

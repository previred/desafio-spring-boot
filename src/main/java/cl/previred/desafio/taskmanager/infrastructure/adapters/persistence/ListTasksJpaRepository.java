package cl.previred.desafio.taskmanager.infrastructure.adapters.persistence;

import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.ListTasksRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.mapper.TaskJpaMapper;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ListTasksJpaRepository implements ListTasksRepository {

    private final TaskJpaRepository repository;
    private final TaskJpaMapper taskMapper;

    @Override
    public List<Task> retrieve() {
        return repository.findAll().stream()
                .map(taskMapper::toDomain)
                .map(task -> (Task) task)
                .toList();
    }
}

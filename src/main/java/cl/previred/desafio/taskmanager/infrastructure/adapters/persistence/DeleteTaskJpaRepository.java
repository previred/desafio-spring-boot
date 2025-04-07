package cl.previred.desafio.taskmanager.infrastructure.adapters.persistence;

import cl.previred.desafio.taskmanager.domain.ports.out.DeleteTaskRepository;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskJpaEntity;
import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories.TaskJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class DeleteTaskJpaRepository implements DeleteTaskRepository {

    TaskJpaRepository repository;

    @Override
    public boolean delete(String id) {
        Optional<TaskJpaEntity> taskJpa = repository.findById(id);

        if (taskJpa.isEmpty()){
            return false;
        }

        repository.delete(taskJpa.get());

        return true;
    }
}

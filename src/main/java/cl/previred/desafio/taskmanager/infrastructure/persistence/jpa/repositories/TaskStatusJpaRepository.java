package cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.repositories;

import cl.previred.desafio.taskmanager.infrastructure.persistence.jpa.entities.TaskStatusJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusJpaRepository extends JpaRepository<TaskStatusJpaEntity, Long> {
    Optional<TaskStatusJpaEntity> findByName(String name);
}

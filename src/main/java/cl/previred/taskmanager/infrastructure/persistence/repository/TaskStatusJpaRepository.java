package cl.previred.taskmanager.infrastructure.persistence.repository;

import cl.previred.taskmanager.infrastructure.persistence.entity.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TaskStatusJpaRepository extends JpaRepository<TaskStatusEntity, Long> {
    Optional<TaskStatusEntity> findByName(String name);
}

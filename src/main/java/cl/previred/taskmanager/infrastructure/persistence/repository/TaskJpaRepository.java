package cl.previred.taskmanager.infrastructure.persistence.repository;

import cl.previred.taskmanager.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long> {
}

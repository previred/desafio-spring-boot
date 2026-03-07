package cl.cediaz.desafiospringboot.repository;

import cl.cediaz.desafiospringboot.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    Optional<TaskStatus> findByNameIgnoreCase(String name);
}


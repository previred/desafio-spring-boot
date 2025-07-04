package app.desafio.spring.boot.infrastructure.persistence;

import app.desafio.spring.boot.domain.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    Optional<TaskStatus> findByStatus(String status);

}

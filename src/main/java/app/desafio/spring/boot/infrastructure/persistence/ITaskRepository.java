package app.desafio.spring.boot.infrastructure.persistence;

import app.desafio.spring.boot.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);
    List<Task> findByUserId(Long userId);
    List<Task> findByStatus_Status(String statusName);

}

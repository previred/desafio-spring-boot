package io.github.srdejo.task_manager.repository;

import io.github.srdejo.task_manager.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}

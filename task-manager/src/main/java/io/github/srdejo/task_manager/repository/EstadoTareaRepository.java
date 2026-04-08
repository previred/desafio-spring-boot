package io.github.srdejo.task_manager.repository;

import io.github.srdejo.task_manager.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
}

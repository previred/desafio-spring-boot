package com.yoserp1.tareas.jpa.repository;

import com.yoserp1.tareas.jpa.entity.EstadoTareaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTareaEntity, Long> {
  Optional<EstadoTareaEntity> findByNombre(String estado);
}

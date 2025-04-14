package com.nuevospa.taskmanager.repository;

import com.nuevospa.taskmanager.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    Optional<EstadoTarea> findByNombre(String nombre);
}
package com.nuevospa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nuevospa.model.EstadoTarea;

@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {

    // Método para obtener un estado tarea activo según un id
    Optional<EstadoTarea> findByIdAndActivoTrue(Long id);
}

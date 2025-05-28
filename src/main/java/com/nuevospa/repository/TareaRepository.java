package com.nuevospa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nuevospa.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Método para obtener todas las tareas activas
    List<Tarea> findByActivoTrue();

    // Método para obtener una tarea activa según un id
    Optional<Tarea> findByIdAndActivoTrue(Long id);
}

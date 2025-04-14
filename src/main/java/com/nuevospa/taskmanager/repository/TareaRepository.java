package com.nuevospa.taskmanager.repository;

import com.nuevospa.taskmanager.entity.Tarea;
import com.nuevospa.taskmanager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByEstadoTarea_Nombre(String nombre);
    Optional<Tarea> findById(Long id);
}
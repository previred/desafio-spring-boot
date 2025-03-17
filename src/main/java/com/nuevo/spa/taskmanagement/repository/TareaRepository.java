package com.nuevo.spa.taskmanagement.repository;

import com.nuevo.spa.taskmanagement.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    boolean existsById(Long id);
    Page<Tarea> findAll(Pageable pageable);
}
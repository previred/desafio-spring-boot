package com.ejemplo.spa.repository;

import com.ejemplo.spa.model.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {}
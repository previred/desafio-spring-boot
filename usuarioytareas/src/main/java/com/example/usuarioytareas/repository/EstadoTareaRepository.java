package com.example.usuarioytareas.repository;


import com.example.usuarioytareas.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    
}

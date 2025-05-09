package com.example.usuarioytareas.repository;

import com.example.usuarioytareas.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public  interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuarioId(Long usuarioId);
    
}

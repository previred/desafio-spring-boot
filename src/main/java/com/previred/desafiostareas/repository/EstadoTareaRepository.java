package com.previred.desafiostareas.repository;

import com.previred.desafiostareas.repository.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio  entidad EstadoTarea
 */
@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    
    Optional<EstadoTarea> findByNombre(String nombre);
}

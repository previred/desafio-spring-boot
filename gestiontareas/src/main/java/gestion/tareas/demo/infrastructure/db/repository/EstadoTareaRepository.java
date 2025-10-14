package gestion.tareas.demo.infrastructure.db.repository;

import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    Optional<EstadoTarea> findByNombreIgnoreCase(String nombre);
}

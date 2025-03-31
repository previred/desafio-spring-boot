package gestion.tareas.demo.repository;

import gestion.tareas.demo.model.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    Optional<EstadoTarea> findByNombreIgnoreCase(String nombre);
}

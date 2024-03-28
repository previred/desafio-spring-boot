package cl.rhoffmann.previred.desafiospringboot.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;

/**
 * Repositorio para la entidad EstadoTarea, proporcionando operaciones CRUD estándar.
 * <p>
 * Extiende {@link JpaRepository} para habilitar la gestión automática de operaciones CRUD para EstadoTarea.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {

}

package dsegovia.desafio.repository;

import dsegovia.desafio.entity.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para operaciones con estados de tarea
 */
@Repository
public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {

    /**
     * Busca un estado por su nombre
     *
     * @param nombre Nombre del estado a buscar
     * @return Optional con el estado si existe
     */
    Optional<EstadoTarea> findByNombre(String nombre);
}

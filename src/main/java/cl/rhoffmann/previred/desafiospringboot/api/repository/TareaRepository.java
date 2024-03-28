package cl.rhoffmann.previred.desafiospringboot.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.rhoffmann.previred.desafiospringboot.api.entity.Tarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;

/**
 * Repositorio para la entidad Tarea, extendiendo las operaciones CRUD básicas con funcionalidades adicionales.
 * <p>
 * Provee acceso a las operaciones de base de datos para las tareas, incluyendo métodos personalizados para
 * buscar tareas por usuario y encontrar una tarea específica por ID y usuario, facilitando así la gestión de tareas
 * en contextos multiusuario.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
	/**
     * Encuentra todas las tareas asociadas a un usuario específico, soportando paginación.
     *
     * @param usuario   El usuario por el cual filtrar las tareas.
     * @param pageable  La configuración de paginación.
     * @return Una página de tareas pertenecientes al usuario dado.
     */
	Page<Tarea> findByUsuario(Usuario usuario, Pageable pageable);
	
	/**
     * Busca una tarea específica por su ID, asegurando además que pertenezca a un usuario específico.
     *
     * @param id     El ID de la tarea.
     * @param usuario El usuario asociado a la tarea.
     * @return Un Optional que contiene la tarea si es encontrada y pertenece al usuario especificado.
     */
	Optional<Tarea> findByIdAndUsuario(Long id, Usuario usuario);
}

package dsegovia.desafio.repository;

import dsegovia.desafio.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para operaciones con tareas
 */
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    /**
     * Busca todas las tareas de un usuario
     *
     * @param ownerId ID del propietario de las tareas
     * @return Lista de tareas del usuario
     */
    List<Tarea> findByOwnerId(Long ownerId);

    /**
     * Busca tareas por propietario y estado
     *
     * @param ownerId ID del propietario
     * @param estadoId ID del estado
     * @return Lista de tareas que cumplen los criterios
     */
    List<Tarea> findByOwnerIdAndEstadoId(Long ownerId, Long estadoId);

    /**
     * Busca tareas por propietario y prioridad
     *
     * @param ownerId ID del propietario
     * @param prioridad Prioridad de las tareas
     * @return Lista de tareas que cumplen los criterios
     */
    List<Tarea> findByOwnerIdAndPrioridad(Long ownerId, Tarea.Prioridad prioridad);

    /**
     * Busca tareas próximas a vencer
     *
     * @param fecha Fecha límite para considerar vencimiento próximo
     * @return Lista de tareas próximas a vencer
     */
    @Query("SELECT t FROM Tarea t WHERE t.fechaVencimiento < :fecha AND t.estado.nombre != 'COMPLETADA' AND t.estado.nombre != 'CANCELADA'")
    List<Tarea> findTareasPorVencer(@Param("fecha") LocalDateTime fecha);

    /**
     * Busca tareas que contengan una etiqueta específica
     *
     * @param ownerId ID del propietario
     * @param etiqueta Etiqueta a buscar
     * @return Lista de tareas que contienen la etiqueta
     */
    @Query("SELECT t FROM Tarea t WHERE t.owner.id = :ownerId AND t.etiquetas LIKE %:etiqueta%")
    List<Tarea> findByOwnerIdAndEtiquetaContaining(@Param("ownerId") Long ownerId, @Param("etiqueta") String etiqueta);
}

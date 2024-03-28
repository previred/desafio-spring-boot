package cl.rhoffmann.previred.desafiospringboot.api.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPostDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPutDTO;
import java.util.List;

/**
 * Servicio para gestionar tareas, proporcionando operaciones CRUD.
 * <p>
 * Define las operaciones para manejar tareas, incluyendo la recuperación de todas las tareas con soporte de paginación,
 * la obtención de una tarea por ID, la creación, actualización y eliminación de tareas.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Service
public interface TareaService {

	/**
     * Obtiene todas las tareas disponibles, soportando la paginación.
     *
     * @param pageable Configuración de paginación.
     * @return Lista de {@link TareaDTO} con las tareas paginadas.
     */
	@Transactional(readOnly = true)
	public List<TareaDTO> obtenerTodos(Pageable pageable);

	/**
     * Obtiene los detalles de una tarea específica por su ID.
     *
     * @param id El identificador de la tarea.
     * @return {@link TareaDTO} con los detalles de la tarea.
     */
	@Transactional(readOnly = true)
	public TareaDTO obtenerPorId(Long id);

	/**
     * Crea una nueva tarea basada en la información proporcionada.
     *
     * @param tareaPostDTO Datos para la creación de la tarea.
     * @return {@link TareaDTO} con los detalles de la tarea creada.
     */
	@Transactional
	public TareaDTO crear(TareaPostDTO tareaPostDTO);

	/**
     * Actualiza una tarea existente con nuevos datos.
     *
     * @param id           El identificador de la tarea a actualizar.
     * @param tareaPutDTO  Datos para la actualización de la tarea.
     * @return {@link TareaDTO} con los detalles actualizados de la tarea.
     */
	@Transactional
	public TareaDTO actualizar(Long id, TareaPutDTO tareaPutDTO);

	/**
     * Elimina una tarea específica por su ID.
     *
     * @param id El identificador de la tarea a eliminar.
     */
	@Transactional
	public void eliminar(Long id);

}

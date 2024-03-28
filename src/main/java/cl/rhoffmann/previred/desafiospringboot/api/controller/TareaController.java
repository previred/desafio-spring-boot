package cl.rhoffmann.previred.desafiospringboot.api.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPostDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPutDTO;
import cl.rhoffmann.previred.desafiospringboot.api.serviceimpl.TareaServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * Controlador REST para manejar las operaciones CRUD de Tareas.
 * <p>
 * Este controlador proporciona endpoints para la creación, lectura, actualización y eliminación de tareas,
 * permitiendo interactuar con la entidad de Tarea a través de operaciones definidas en el servicio {@link TareaServiceImpl}.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@RestController
@RequestMapping("/api/tareas")
public class TareaController {

	private final TareaServiceImpl tareaServiceImpl;

    /**
     * Constructor para inyectar la implementación del servicio de tareas.
     *
     * @param tareaServiceImpl Servicio que maneja la lógica de negocio para las tareas.
     */
    public TareaController(TareaServiceImpl tareaServiceImpl) {
        this.tareaServiceImpl = tareaServiceImpl;
    }

    /**
     * Obtiene una lista paginada de tareas.
     * <p>
     * {@code GET /api/tareas} : Recupera todas las tareas disponibles.
     * </p>
     *
     * @param pageable Parámetro para controlar la paginación de la respuesta.
     * @return Lista de tareas paginada.
     */
    @GetMapping
    public ResponseEntity<List<TareaDTO>> obtenerTareas(Pageable pageable) {
        List<TareaDTO> tareas = tareaServiceImpl.obtenerTodos(pageable);
        return ResponseEntity.ok(tareas);
    }

    /**
     * Obtiene una tarea específica por su ID.
     * <p>
     * {@code GET /api/tareas/{id}} : Recupera los detalles de una tarea específica.
     * </p>
     *
     * @param id Identificador único de la tarea a recuperar.
     * @return Tarea encontrada o estado NOT_FOUND si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(@PathVariable Long id) {
        TareaDTO tarea = tareaServiceImpl.obtenerPorId(id);
        return ResponseEntity.ok(tarea);
    }

    /**
     * Crea una nueva tarea.
     * <p>
     * {@code POST /api/tareas} : Crea y devuelve una nueva tarea basada en los datos proporcionados.
     * </p>
     *
     * @param tareaPostDTO Datos necesarios para crear la nueva tarea.
     * @return La tarea creada.
     */
    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@RequestBody TareaPostDTO tareaPostDTO) {
        TareaDTO nuevoTarea = tareaServiceImpl.crear(tareaPostDTO);
        return ResponseEntity.ok(nuevoTarea);
    }

    /**
     * Actualiza una tarea existente.
     * <p>
     * {@code PUT /api/tareas/{id}} : Actualiza y devuelve una tarea basada en el ID proporcionado.
     * </p>
     *
     * @param id          Identificador único de la tarea a actualizar.
     * @param tareaPutDTO Datos para actualizar la tarea existente.
     * @return La tarea actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Long id, @RequestBody TareaPutDTO tareaPutDTO) {
        TareaDTO tareaActualizado = tareaServiceImpl.actualizar(id, tareaPutDTO);
        return ResponseEntity.ok(tareaActualizado);
    }

    /**
     * Elimina una tarea específica.
     * <p>
     * {@code DELETE /api/tareas/{id}} : Elimina la tarea especificada por el ID.
     * </p>
     *
     * @param id Identificador único de la tarea a eliminar.
     * @return Respuesta sin contenido si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaServiceImpl.eliminar(id);
        return ResponseEntity.ok().build();
    }
}

package dsegovia.desafio.controller;

import dsegovia.desafio.dto.TareaRequest;
import dsegovia.desafio.dto.TareaResponse;
import dsegovia.desafio.entity.Tarea;
import dsegovia.desafio.exception.AccesoDenegadoException;
import dsegovia.desafio.exception.RecursoNoEncontradoException;
import dsegovia.desafio.mapper.TareaMapper;
import dsegovia.desafio.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para la gestión de tareas
 */
@RestController
@RequestMapping("/api/tareas")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class TareaController {

    private final TareaService tareaService;
    private final TareaMapper tareaMapper;

    @Autowired
    public TareaController(TareaService tareaService, TareaMapper tareaMapper) {
        this.tareaService = tareaService;
        this.tareaMapper = tareaMapper;
    }

    /**
     * Obtiene todas las tareas del usuario actual con posibilidad de filtrado
     */
    @GetMapping
    public ResponseEntity<List<TareaResponse>> listarTareas(
            @RequestParam(required = false) Long estadoId,
            @RequestParam(required = false) Tarea.Prioridad prioridad,
            @RequestParam(required = false) String etiqueta) {

        List<Tarea> tareas = tareaService.obtenerTareasFiltradas(estadoId, prioridad, etiqueta);

        List<TareaResponse> tareaResponses = tareas.stream()
                .map(tareaMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tareaResponses);
    }

    /**
     * Obtiene una tarea específica por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> obtenerTarea(@PathVariable Long id) {
        Tarea tarea = tareaService.obtenerTareaById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tarea", id));

        // Verificar permisos
        if (!tareaService.tienePermiso(tarea, tareaService.obtenerUsuarioActual())) {
            throw new AccesoDenegadoException("No tienes permiso para ver esta tarea");
        }

        return ResponseEntity.ok(tareaMapper.toDto(tarea));
    }

    /**
     * Crea una nueva tarea
     */
    @PostMapping
    public ResponseEntity<TareaResponse> crearTarea(@Valid @RequestBody TareaRequest tareaRequest) {
        Tarea tarea = tareaMapper.toEntity(tareaRequest);
        Tarea tareaCreada = tareaService.crearTarea(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaMapper.toDto(tareaCreada));
    }

    /**
     * Actualiza una tarea existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<TareaResponse> actualizarTarea(@PathVariable Long id,
                                                         @Valid @RequestBody TareaRequest tareaRequest) {
        try {
            Tarea tarea = tareaMapper.toEntity(tareaRequest);
            Tarea tareaActualizada = tareaService.actualizarTarea(id, tarea);
            return ResponseEntity.ok(tareaMapper.toDto(tareaActualizada));
        } catch (SecurityException e) {
            throw new AccesoDenegadoException(e.getMessage());
        } catch (Exception e) {
            throw new RecursoNoEncontradoException(e.getMessage());
        }
    }

    /**
     * Elimina una tarea
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        try {
            tareaService.eliminarTarea(id);
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            throw new AccesoDenegadoException(e.getMessage());
        } catch (Exception e) {
            throw new RecursoNoEncontradoException(e.getMessage());
        }
    }
}
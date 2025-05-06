package dsegovia.desafio.dto;

import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.entity.Tarea.Prioridad;
import dsegovia.desafio.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para devolver información completa de tareas
 */
@Getter
@Setter
public class TareaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVencimiento;
    private Prioridad prioridad;
    private Long ownerId;
    private Long estadoId;
    private List<String> etiquetas;
    private String estadoNombre;
    private String ownerUsername;
}
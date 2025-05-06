package dsegovia.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsegovia.desafio.entity.Tarea.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para recibir datos de creación o actualización de tareas
 */
@Getter
@Setter
public class TareaRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede exceder los 100 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @JsonProperty("fechaVencimiento")
    private LocalDateTime fechaVencimiento;

    private Prioridad prioridad;

    @NotNull(message = "El estado es obligatorio")
    @JsonProperty("estadoId")
    private Long estadoId;

    private List<String> etiquetas;
}
package cl.previred.desafio.taskmanager.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTaskRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    @NotBlank(message = "El Usuario creador es obligatorio")
    @Size(max = 100, message = "El usuraio no puede exceder 100 caracteres")
    private String createdBy;
}

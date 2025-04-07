package cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea;

import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.models.Usuario;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TareaUpdateRequest(
        @Min(1)
        Long id,
        @NotBlank
        String nombre,
        String descripcion,
        Long estadoTareaId,
        Long usuarioAsignadoId
) {
    public Tarea toDomain(){
        return Tarea.builder()
                .id(this.id)
                .nombre(this.nombre)
                .description(this.descripcion)
                .estado(estadoTareaId!=null? EstadoTarea.builder().id(estadoTareaId).build():null)
                .usuarioAsignado(Usuario.builder().id(usuarioAsignadoId).build())
                .build();
    }
}

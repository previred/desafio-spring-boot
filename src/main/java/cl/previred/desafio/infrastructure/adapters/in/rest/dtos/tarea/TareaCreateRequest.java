package cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea;

import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.models.Usuario;
import jakarta.validation.constraints.NotBlank;

public record TareaCreateRequest(
        @NotBlank
        String nombre,
        String descripcion,
        Long estadoTareaId,
        Long usuarioAsignadoId
) {
    public Tarea toDomain(){
        return Tarea.builder()
                .nombre(this.nombre)
                .description(this.descripcion)
                .estado(EstadoTarea.builder().id(this.estadoTareaId).build())
                .usuarioAsignado(usuarioAsignadoId!=null?Usuario.builder().id(usuarioAsignadoId).build():null)
                .build();
    }
}

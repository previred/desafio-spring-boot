package cl.previred.desafio.infrastructure.adapters.in.rest.dtos.tarea;

import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.infrastructure.adapters.in.rest.dtos.usuario.UsuarioResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TareaResponse(
    Long id,
    String nombre,
    String descripcion,
    String estado,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime fechaCreacion,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime fechaAsignacion,
    UsuarioResponse usuarioCreador,
    UsuarioResponse usuarioAsignado
) {

    public static TareaResponse fromDomain(Tarea domain) {
        return new TareaResponse(
            domain.getId(),
            domain.getNombre(),
            domain.getDescription(),
            domain.getEstado().getNombre(),
            domain.getFechaCreacion(),
            domain.getFechaAsignacion(),
            UsuarioResponse.fromDomain(domain.getUsuarioCreador()),
            domain.getUsuarioAsignado()!=null?UsuarioResponse.fromDomain(domain.getUsuarioAsignado()):null
        );
    }
}

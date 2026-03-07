package com.previred.desafiostareas.mapper;

import com.previred.desafiostareas.controller.dto.TareaRequest;
import com.previred.desafiostareas.controller.dto.TareaResponse;
import com.previred.desafiostareas.repository.entity.EstadoTarea;
import com.previred.desafiostareas.repository.entity.Prioridad;
import com.previred.desafiostareas.repository.entity.TareaEntity;
import com.previred.desafiostareas.repository.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Mapper para convertir entre entidades y DTOs de TareaEntity.
 */
@Component
public class MapeadorTarea {

    public TareaResponse toRespuesta(TareaEntity tareaEntity) {
        return TareaResponse.builder()
                .id(tareaEntity.getId())
                .titulo(tareaEntity.getTitulo())
                .descripcion(tareaEntity.getDescripcion())
                .fechaCreacion(toOffsetDateTime(tareaEntity.getFechaCreacion()))
                .prioridad(tareaEntity.getPrioridad().name())
                .estadoNombre(tareaEntity.getEstado().getNombre())
                .nombreUsuario(tareaEntity.getUsuarioEntity().getNombreUsuario())
                .build();
    }

    public TareaEntity toEntidad(TareaRequest request, EstadoTarea estado, UsuarioEntity usuarioEntity) {
        return TareaEntity.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .fechaCreacion(LocalDateTime.now())
                .prioridad(Prioridad.valueOf(request.getPrioridad().getValue()))
                .estado(estado)
                .usuarioEntity(usuarioEntity)
                .build();
    }

    public void actualizarEntidad(TareaEntity tareaEntity, TareaRequest request,
                                  EstadoTarea estado, UsuarioEntity usuarioEntity) {
        if (request.getTitulo() != null) {
            tareaEntity.setTitulo(request.getTitulo());
        }
        if (request.getDescripcion() != null) {
            tareaEntity.setDescripcion(request.getDescripcion());
        }
        if (request.getPrioridad() != null) {
            tareaEntity.setPrioridad(Prioridad.valueOf(request.getPrioridad().getValue()));
        }
        if (estado != null) {
            tareaEntity.setEstado(estado);
        }
        if (usuarioEntity != null) {
            tareaEntity.setUsuarioEntity(usuarioEntity);
        }
    }

    private OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
}

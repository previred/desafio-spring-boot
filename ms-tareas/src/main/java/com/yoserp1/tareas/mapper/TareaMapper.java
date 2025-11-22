package com.yoserp1.tareas.mapper;

import com.yoserp1.tareas.dto.TareaDTO;
import com.yoserp1.tareas.jpa.entity.EstadoTareaEntity;
import com.yoserp1.tareas.jpa.entity.TareaEntity;
import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

  public TareaEntity toEntity(TareaDTO dto) {
    if (dto == null) return null;

    TareaEntity entity = new TareaEntity();
    entity.setTitulo(dto.titulo());
    entity.setDescripcion(dto.descripcion());

    if (dto.estado() != null) {
      EstadoTareaEntity estado = new EstadoTareaEntity();
      estado.setNombre(dto.estado());
      entity.setEstado(estado);
    } else {
      entity.setEstado(null);
    }

    entity.setFechaCreacion(dto.fechaCreacion());

    if (dto.usuarioId() != null) {
      UsuarioEntity usuario = new UsuarioEntity();
      usuario.setId(dto.usuarioId());
      entity.setUsuario(usuario);
    }

    return entity;
  }

  public TareaDTO toDTO(TareaEntity entity) {
    if (entity == null) return null;

    Long usuarioId = entity.getUsuario() != null ? entity.getUsuario().getId() : null;
    String estadoNombre = entity.getEstado() != null ? entity.getEstado().getNombre() : null;

    return new TareaDTO(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        estadoNombre,
        entity.getFechaCreacion(),
        usuarioId);
  }
}

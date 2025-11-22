package com.yoserp1.tareas.mapper;

import com.yoserp1.tareas.dto.TareaRequestDTO;
import com.yoserp1.tareas.dto.TareaResponseDTO;
import com.yoserp1.tareas.dto.UsuarioDTO;
import com.yoserp1.tareas.jpa.entity.EstadoTareaEntity;
import com.yoserp1.tareas.jpa.entity.TareaEntity;
import com.yoserp1.tareas.jpa.entity.UsuarioEntity;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

  public TareaEntity toEntity(TareaRequestDTO dto) {
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

  public TareaResponseDTO toDTO(TareaEntity entity) {
    if (entity == null) return null;

    String estadoNombre =
        Optional.ofNullable(entity.getEstado()).map(EstadoTareaEntity::getNombre).orElse(null);

    UsuarioDTO usuario =
        Optional.ofNullable(entity.getUsuario())
            .map(
                u ->
                    new UsuarioDTO(
                        u.getUsername(), u.getNombreCompleto(), u.getEmail(), u.isActivo()))
            .orElse(null);

    return new TareaResponseDTO(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        estadoNombre,
        entity.getFechaCreacion(),
        usuario);
  }
}

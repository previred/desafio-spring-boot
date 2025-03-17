package com.nuevo.spa.taskmanagement.mapper;

import com.nuevo.spa.taskmanagement.generated.model.EstadoTareaDto;
import com.nuevo.spa.taskmanagement.generated.model.TareaDto;
import com.nuevo.spa.taskmanagement.generated.model.UsuarioDto;
import com.nuevo.spa.taskmanagement.model.EstadoTarea;
import com.nuevo.spa.taskmanagement.model.Tarea;
import com.nuevo.spa.taskmanagement.model.Usuario;

import java.util.Optional;

public class TareaMapper {

    public static Tarea toEntity(TareaDto dto) {
        if (dto == null) {
            return null;
        }
        return Tarea.builder()
                .id(Optional.ofNullable(dto.getId()).map(Integer::longValue).orElse(null))
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .usuario(Optional.ofNullable(dto.getUsuario())
                        .map(userDto -> new Usuario(userDto.getId().longValue()))
                        .orElse(null))
                .estado(Optional.ofNullable(dto.getEstado())
                        .map(estadoDto -> new EstadoTarea(estadoDto.getId().longValue()))
                        .orElse(null))
                .build();
    }

    public static TareaDto toDTO(Tarea entity) {
        if (entity == null) {
            return null;
        }
        TareaDto dto = new TareaDto();
        dto.setId(Optional.ofNullable(entity.getId()).map(Long::intValue).orElse(null));
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setUsuario(Optional.ofNullable(entity.getUsuario()).map(TareaMapper::toUsuarioDTO).orElse(null));
        dto.setEstado(Optional.ofNullable(entity.getEstado()).map(TareaMapper::toEstadoDTO).orElse(null));
        return dto;
    }

    public static UsuarioDto toUsuarioDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDto dto = new UsuarioDto();
        dto.setId(Optional.ofNullable(usuario.getId()).map(Long::intValue).orElse(null));
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    public static EstadoTareaDto toEstadoDTO(EstadoTarea estado) {
        if (estado == null) {
            return null;
        }
        EstadoTareaDto dto = new EstadoTareaDto();
        dto.setId(Optional.ofNullable(estado.getId()).map(Long::intValue).orElse(null));
        dto.setNombre(estado.getNombre());
        return dto;
    }
}

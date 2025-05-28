package com.nuevospa.mapper;

import org.springframework.stereotype.Component;

import com.nuevospa.model.EstadoTarea;
import com.nuevospa.model.Tarea;
import com.nuevospa.model.TareaDto;
import com.nuevospa.model.TareaNuevaDto;
import com.nuevospa.model.Usuario;

@Component
public class TareaMapper {
    
    public TareaDto toDto(Tarea tarea) {
        if (tarea == null) return null;

        TareaDto tareaDto = new TareaDto();
        tareaDto.setId(tarea.getId());
        tareaDto.setNombre(tarea.getNombre());
        tareaDto.setDescripcion(tarea.getDescripcion());
        tareaDto.setUsuarioId(tarea.getUsuario().getId());
        tareaDto.setUsuarioCorreo(tarea.getUsuario().getCorreo());
        tareaDto.setEstadoTareaId(tarea.getEstadoTarea().getId());
        tareaDto.setEstadoTareaNombre(tarea.getEstadoTarea().getNombre());
        return tareaDto;
    }

    public Tarea toEntity(Tarea tarea, TareaNuevaDto tareaNuevaDto, Usuario usuario, EstadoTarea estadoTarea) {
        tarea.setNombre(tareaNuevaDto.getNombre());
        tarea.setDescripcion(tareaNuevaDto.getDescripcion());
        tarea.setUsuario(usuario);
        tarea.setEstadoTarea(estadoTarea);
        return tarea;
    }
}

package com.ejemplo.spa.mapper;

import com.ejemplo.spa.dto.TareaDTO;
import com.ejemplo.spa.model.Tarea;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {
    public TareaDTO toDTO(Tarea tarea) {
        TareaDTO dto = new TareaDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setUsuarioId(tarea.getUsuario().getId());
        dto.setUserName(tarea.getUsuario().getUsername());
        dto.setEstadoId(tarea.getEstado().getId());
        dto.setEstadoName(tarea.getEstado().getNombre());
        return dto;
    }
}
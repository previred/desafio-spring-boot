package com.nuevo.spa.taskmanagement.validation;

import com.nuevo.spa.taskmanagement.generated.model.TareaDto;
import org.springframework.stereotype.Component;

@Component
public class TareaValidator {

    public void validarTareaDto(TareaDto tareaDTO) {
        if (tareaDTO == null) {
            throw new IllegalArgumentException("El objeto TareaDto no puede ser nulo.");
        }
        if (tareaDTO.getTitulo() == null || tareaDTO.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio.");
        }
        if (tareaDTO.getDescripcion() == null || tareaDTO.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción de la tarea es obligatoria.");
        }
        if (tareaDTO.getUsuario() == null || tareaDTO.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El usuario de la tarea es obligatorio.");
        }
        if (tareaDTO.getEstado() == null || tareaDTO.getEstado().getId() == null) {
            throw new IllegalArgumentException("El estado de la tarea es obligatorio.");
        }
    }
}

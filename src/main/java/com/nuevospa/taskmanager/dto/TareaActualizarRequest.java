package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.entity.EstadoTarea;
import lombok.Getter;
import lombok.Setter;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class TareaActualizarRequest {

    @NotBlank
    private String nombre;
    
    @NotBlank
    private String descripcion;
    
    @NotNull
    private EstadoTarea estado;
}

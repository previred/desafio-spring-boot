package com.nuevospa.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTareaRequest {
	
    private String tituloTarea;
    private String descripcionTarea;
    private Long idUsuario;;
}

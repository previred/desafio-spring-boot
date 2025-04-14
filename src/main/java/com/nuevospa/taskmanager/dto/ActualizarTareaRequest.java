package com.nuevospa.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarTareaRequest {
	
	private Long idTarea;
    private String tituloTarea;
    private String descripcionTarea;
    private String estadoTarea;
}

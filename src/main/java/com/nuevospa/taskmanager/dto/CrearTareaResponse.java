package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.dto.TareaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearTareaResponse extends RespuestaBase {
	
	private Long idUsuario;
    private TareaDTO tarea;
    
    public CrearTareaResponse() {
        this.tarea = new TareaDTO(); 
    }
}
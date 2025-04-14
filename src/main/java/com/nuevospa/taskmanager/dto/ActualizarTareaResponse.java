package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.dto.TareaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarTareaResponse extends RespuestaBase{
	
    private TareaDTO tarea;
    
    public ActualizarTareaResponse() {
        this.tarea = new TareaDTO(); 
    }
}

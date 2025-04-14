package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.dto.TareaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EliminarTareaResponse extends RespuestaBase{
	
    private TareaDTO tarea;
    
    public EliminarTareaResponse() {
        this.tarea = new TareaDTO(); 
    }
}

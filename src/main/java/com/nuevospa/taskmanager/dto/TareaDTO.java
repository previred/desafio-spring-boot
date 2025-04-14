package com.nuevospa.taskmanager.dto;

import com.nuevospa.taskmanager.dto.TareaDTO;
import com.nuevospa.taskmanager.dto.EstadoTareaDTO;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TareaDTO {
	
    private Long id;
    private String titulo;
    private String descripcion;
    private EstadoTareaDTO estadoTarea;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion; 
    
    
    public TareaDTO() {
        this.estadoTarea = new EstadoTareaDTO(); 
    }

}

package com.nuevospa.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoTareaResponse extends RespuestaBase {
    private Long id;
    private String nombre;
}
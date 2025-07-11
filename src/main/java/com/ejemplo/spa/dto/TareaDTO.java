package com.ejemplo.spa.dto;

import lombok.Data;

@Data
public class TareaDTO {
    private long id;
    private String titulo;
    private String descripcion;
    private Long usuarioId;
    private String userName;
    private Long estadoId;
    private String estadoName;


}


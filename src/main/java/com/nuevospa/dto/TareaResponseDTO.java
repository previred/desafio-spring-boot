package com.nuevospa.dto;

public record TareaResponseDTO(
        Long id,
        String titulo,
        String descripcion,
        String estado,
        String usuario
) {

}

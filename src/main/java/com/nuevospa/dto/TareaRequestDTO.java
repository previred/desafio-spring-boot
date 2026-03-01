package com.nuevospa.dto;

public record TareaRequestDTO(
        String titulo,
        String descripcion,
        Long estadoId
) {
}

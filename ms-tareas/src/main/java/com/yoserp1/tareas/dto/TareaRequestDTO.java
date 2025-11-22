package com.yoserp1.tareas.dto;

import java.time.LocalDateTime;

public record TareaRequestDTO(
    Long id,
    String titulo,
    String descripcion,
    String estado,
    LocalDateTime fechaCreacion,
    Long usuarioId) {}

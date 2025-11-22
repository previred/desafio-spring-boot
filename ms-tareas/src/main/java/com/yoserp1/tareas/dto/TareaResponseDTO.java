package com.yoserp1.tareas.dto;

import java.time.LocalDateTime;

public record TareaResponseDTO(
    Long id,
    String titulo,
    String descripcion,
    String estado,
    LocalDateTime fechaCreacion,
    UsuarioDTO usuario) {}

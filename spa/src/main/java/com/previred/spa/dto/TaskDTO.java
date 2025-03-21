package com.previred.spa.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String description;
    private String status;
    private Long userId; // ID del usuario asociado a la tarea
}
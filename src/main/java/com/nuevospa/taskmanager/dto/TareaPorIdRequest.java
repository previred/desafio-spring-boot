package com.nuevospa.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class TareaPorIdRequest {
    
    @NotNull
    private Long id;
}

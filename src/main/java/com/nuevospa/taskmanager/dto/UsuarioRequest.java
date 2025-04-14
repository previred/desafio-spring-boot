package com.nuevospa.taskmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nombre;
    private String email;
    private String password;
}
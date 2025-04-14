package com.nuevospa.taskmanager.service;

import com.nuevospa.taskmanager.entity.Usuario;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNombre(String nombre);
}
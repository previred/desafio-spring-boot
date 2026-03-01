package com.nuevospa.service;

import com.nuevospa.domain.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);
}

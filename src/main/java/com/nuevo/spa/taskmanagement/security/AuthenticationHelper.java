package com.nuevo.spa.taskmanagement.security;

import com.nuevo.spa.taskmanagement.exception.AuthenticationException;
import com.nuevo.spa.taskmanagement.model.Usuario;
import com.nuevo.spa.taskmanagement.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationHelper {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationHelper(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario verifyCredentials(String username, String password) {
        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new AuthenticationException("Credenciales incorrectas");
        }

        return usuario;
    }
}

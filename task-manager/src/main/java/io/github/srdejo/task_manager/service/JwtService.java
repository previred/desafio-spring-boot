package io.github.srdejo.task_manager.service;

import io.github.srdejo.task_manager.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(Usuario usuario);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);
}

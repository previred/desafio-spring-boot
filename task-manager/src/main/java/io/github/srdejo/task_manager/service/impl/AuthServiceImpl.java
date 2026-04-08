package io.github.srdejo.task_manager.service.impl;

import io.github.srdejo.task_manager.entity.Usuario;
import io.github.srdejo.task_manager.controller.exception.ResourceNotFoundException;
import io.github.srdejo.task_manager.controller.exception.UnauthorizedException;
import io.github.srdejo.task_manager.model.AuthRequest;
import io.github.srdejo.task_manager.model.AuthResponse;
import io.github.srdejo.task_manager.repository.UsuarioRepository;
import io.github.srdejo.task_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;

    @Override
    public AuthResponse login(AuthRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(usuario);

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        return response;
    }
}

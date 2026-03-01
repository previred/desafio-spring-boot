package com.nuevospa.controller;

import com.nuevospa.dto.AutenRequestDTO;
import com.nuevospa.dto.AutenResponseDTO;
import com.nuevospa.dto.RefreshTokenRequestDTO;
import com.nuevospa.domain.Usuario;
import com.nuevospa.exception.BusinessException;
import com.nuevospa.repository.UsuarioRepository;
import com.nuevospa.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AutenResponseDTO login(@RequestBody AutenRequestDTO request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (Exception e) {
            throw new BusinessException("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = usuarioRepo.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        String accessToken = jwtService.generateToken(usuario.getUsername());
        String refreshToken = jwtService.generateRefreshToken(usuario.getUsername());

        return new AutenResponseDTO(accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public AutenResponseDTO refresh(@RequestBody RefreshTokenRequestDTO request) {
        String username = jwtService.extractUsername(request.refreshToken());
        String accessToken = jwtService.generateToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);
        return new AutenResponseDTO (accessToken, refreshToken);
    }
}
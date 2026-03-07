package com.previred.desafiostareas.service;


import com.previred.desafiostareas.controller.dto.LoginRequest;
import com.previred.desafiostareas.controller.dto.LoginResponse;
import com.previred.desafiostareas.repository.UsuarioRepository;
import com.previred.desafiostareas.repository.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio de autenticación de usuarios
 */
@Slf4j
@Service
public class AutenticacionServiceImpl {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AutenticacionServiceImpl(
            UsuarioRepository usuarioRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("Iniciando login para email: {}", request.getEmail());

        try {

            UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

            if (!passwordEncoder.matches(request.getPassword(),usuarioEntity.getPassword())){
                log.warn("Contraseña incorrecta para email: {}", request.getEmail());
                throw new BadCredentialsException("Contraseña incorrecta");
            }


            usuarioEntity.setUltimaConexion(LocalDateTime.now());
            usuarioRepository.save(usuarioEntity);

            String token = jwtService.generateToken(usuarioEntity.getEmail());

            log.info("Login exitoso para usuario: {}", usuarioEntity.getEmail());

            return LoginResponse.builder()
                    .token(token)
                    .username(usuarioEntity.getNombreUsuario())
                    .email(usuarioEntity.getEmail())
                    .build();

        } catch (BadCredentialsException ex) {
            log.warn("Credenciales incorrectas para email: {}", request.getEmail());
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
    }
}
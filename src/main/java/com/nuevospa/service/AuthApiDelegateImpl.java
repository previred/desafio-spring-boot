package com.nuevospa.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nuevospa.model.Usuario;
import com.nuevospa.repository.UsuarioRepository;
import com.nuevospa.util.JwtUtil;

import com.nuevospa.api.AuthApiDelegate;
import com.nuevospa.exception.CamposRequeridosVaciosException;
import com.nuevospa.exception.CredencialesInvalidasException;
import com.nuevospa.model.LoginRequestDto;
import com.nuevospa.model.LoginResponseDto;

@Service
public class AuthApiDelegateImpl implements AuthApiDelegate {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthApiDelegateImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseEntity<LoginResponseDto> authLoginPost(LoginRequestDto loginRequestDto) {
        if(tieneCamposRequeridosInvalidos(loginRequestDto)) {
            throw new CamposRequeridosVaciosException();
        }
        
        Usuario usuario = usuarioRepository.findByCorreoAndActivoTrue(loginRequestDto.getCorreo())
                    .orElseThrow(() -> new CredencialesInvalidasException());

            // Se valida que la contraseña sea correcta
            if (!passwordEncoder.matches(loginRequestDto.getContrasena(), usuario.getContrasena())) {
                throw new CredencialesInvalidasException();
            }

            // Se genera el token jwt
            String token = jwtUtil.generateToken(usuario.getCorreo());

            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setToken(token);
            return ResponseEntity.ok(responseDto);
    }

    private boolean tieneCamposRequeridosInvalidos(LoginRequestDto loginRequestDto) {
        return esNuloOVacio(loginRequestDto.getCorreo()) || esNuloOVacio(loginRequestDto.getContrasena());
    }

    private boolean esNuloOVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}

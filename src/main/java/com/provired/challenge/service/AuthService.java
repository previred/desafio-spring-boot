package com.provired.challenge.service;

import com.provired.challenge.dto.UserRegistrationDTO;
import com.provired.challenge.dto.AuthResponse;
import com.provired.challenge.entity.User;
import com.provired.challenge.exception.ResourceNotFoundException;
import com.provired.challenge.repository.UserRepository;
import com.provired.challenge.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(UserRegistrationDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + request.getUsername()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse("Inicio de sesión exitoso", token);
    }
}
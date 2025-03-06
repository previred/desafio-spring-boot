package com.provired.challenge.controller;

import com.provired.challenge.dto.UserRegistrationDTO;
import com.provired.challenge.dto.AuthResponse;
import com.provired.challenge.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operaciones de autenticación usando JWT")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Inicio de sesión", description = "Genera un token JWT tras autenticar al usuario")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.ok(authService.authenticate(userRegistrationDTO));
    }
}
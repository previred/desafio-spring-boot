package com.nuevo.taskmanager.controller;


import com.nuevo.taskmanager.model.LoginRequest;
import com.nuevo.taskmanager.model.LoginResponse;
import com.nuevo.taskmanager.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthApiController {

    private final AuthService authService;
    public AuthApiController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Petición de login recibida para el usuario: {}", loginRequest.getUsername());
        LoginResponse token = authService.authenticate(loginRequest);
        log.info("Login exitoso para el usuario: {}", loginRequest.getUsername());

        return ResponseEntity.ok(token);
    }
}

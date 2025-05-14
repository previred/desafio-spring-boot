package com.nuevospa.gestiontareas.controller;

import com.nuevospa.gestiontareas.dto.AuthRequest;
import com.nuevospa.gestiontareas.dto.AuthResponse;
import com.nuevospa.gestiontareas.model.User;
import com.nuevospa.gestiontareas.service.AuthService;
import com.nuevospa.gestiontareas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername())
                .filter(u -> u.getPassword().equals(authRequest.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = authService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
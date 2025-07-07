package com.dio.task_manager.presentation;

import com.dio.task_manager.application.dto.AuthRequest;
import com.dio.task_manager.application.dto.AuthResponse;
import com.dio.task_manager.infrastructure.security.JwtService;
import com.dio.task_manager.presentation.exception.AuthenticationException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Hidden
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String username,
            @RequestParam String password
    ) {

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new AuthenticationException("Username o password vacíos");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtService.generateToken(username);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

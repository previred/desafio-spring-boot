package com.example.usuarioytareas.controller;


import com.example.usuarioytareas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;
import com.example.usuarioytareas.dto.AuthRequest;
import com.example.usuarioytareas.dto.AuthResponse;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.username(), request.password()
                )
            );
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            String token = jwtUtil.generateToken(userDetails.getUsername());
            
            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    // DTOs internos
    public record AuthRequest(String username, String password) {}
    public record AuthResponse(String token) {}
}

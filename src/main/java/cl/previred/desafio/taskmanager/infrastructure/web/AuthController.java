package cl.previred.desafio.taskmanager.infrastructure.web;

import cl.previred.desafio.taskmanager.infrastructure.security.AuthService;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.AuthRequest;
import cl.previred.desafio.taskmanager.infrastructure.web.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(
            @RequestBody AuthRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
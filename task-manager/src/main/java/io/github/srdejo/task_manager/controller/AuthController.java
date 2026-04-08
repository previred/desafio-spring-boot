package io.github.srdejo.task_manager.controller;

import io.github.srdejo.task_manager.api.AuthApi;
import io.github.srdejo.task_manager.model.AuthRequest;
import io.github.srdejo.task_manager.model.AuthResponse;
import io.github.srdejo.task_manager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }
}

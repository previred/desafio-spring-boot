package com.api.nuevospa.controller.impl;

import com.api.nuevospa.controller.AuthApi;
import com.api.nuevospa.model.LoginRequest;
import com.api.nuevospa.model.LoginResponse;
import com.api.nuevospa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> authLoginPost(LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}

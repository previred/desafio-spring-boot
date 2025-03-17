package com.nuevo.spa.taskmanagement.controller;

import com.nuevo.spa.taskmanagement.generated.api.AuthApi;
import com.nuevo.spa.taskmanagement.generated.model.LoginUserRequest;
import com.nuevo.spa.taskmanagement.generated.model.LoginUser200Response;
import com.nuevo.spa.taskmanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<LoginUser200Response> loginUser(LoginUserRequest loginUserRequest) {
        if (loginUserRequest == null ||
                loginUserRequest.getUsername() == null || loginUserRequest.getUsername().isBlank() ||
                loginUserRequest.getPassword() == null || loginUserRequest.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String token = authService.authenticate(loginUserRequest.getUsername(), loginUserRequest.getPassword());

            LoginUser200Response response = new LoginUser200Response();
            response.setToken(token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}

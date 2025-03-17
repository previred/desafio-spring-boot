package com.nuevo.spa.taskmanagement.service;

import com.nuevo.spa.taskmanagement.model.Usuario;
import com.nuevo.spa.taskmanagement.security.AuthenticationHelper;
import com.nuevo.spa.taskmanagement.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationHelper authenticationHelper;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationHelper authenticationHelper, JwtUtil jwtUtil) {
        this.authenticationHelper = authenticationHelper;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) {
        Usuario usuario = authenticationHelper.verifyCredentials(username, password);
        return jwtUtil.generateToken(usuario.getUsername());
    }
}


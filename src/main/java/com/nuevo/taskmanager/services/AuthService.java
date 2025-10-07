package com.nuevo.taskmanager.services;


import com.nuevo.taskmanager.repositories.UserRepository;
import com.nuevo.taskmanager.config.SecurityConfig;
import com.nuevo.taskmanager.entities.UserEntity;
import com.nuevo.taskmanager.model.LoginRequest;
import com.nuevo.taskmanager.model.LoginResponse;
import com.nuevo.taskmanager.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SecurityConfig securityConfig;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        log.info("Intento de autenticación para usuario '{}'", loginRequest.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode("123456");
        System.out.println(hashed);
         hashed = encoder.encode("qwerty");
        System.out.println(hashed);

        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> {
                    log.warn("Usuario '{}' no encontrado", loginRequest.getUsername());
                    return new BadCredentialsException("Invalid username or password");
                });

        if (!passwordMatches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Password incorrecto para usuario '{}'", loginRequest.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        log.info("Autenticación exitosa para usuario '{}'", loginRequest.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }
    private boolean passwordMatches(String raw, String encoded) {
        return securityConfig.passwordEncoder().matches(raw, encoded);
    }


}

package cl.previred.desafio.controller;

import cl.previred.desafio.api.AutenticacionApi;
import cl.previred.desafio.model.LoginRequest;
import cl.previred.desafio.model.LoginResponse;
import cl.previred.desafio.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AutenticacionController implements AutenticacionApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<LoginResponse> authLoginPost(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtils.generateToken(authentication.getName());
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            System.err.println(">>> [AUTH ERROR]: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
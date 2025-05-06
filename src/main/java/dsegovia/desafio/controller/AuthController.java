package dsegovia.desafio.controller;

import dsegovia.desafio.dto.LoginRequest;
import dsegovia.desafio.dto.LoginResponse;
import dsegovia.desafio.entity.Rol;
import dsegovia.desafio.entity.Usuario;
import dsegovia.desafio.security.JwtTokenProvider;
import dsegovia.desafio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Controlador para la autenticación de usuarios
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    /**
     * Endpoint para iniciar sesión y obtener un token JWT
     *
     * @param loginRequest Credenciales de usuario
     * @return Token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = authService.findByUsername(loginRequest.getUsername());

        String jwt = tokenProvider.generateToken(usuario);

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setUserId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
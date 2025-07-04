package app.desafio.spring.boot.adapters.rest.web.impl;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import app.desafio.spring.boot.adapters.rest.response.ApiResponse;
import app.desafio.spring.boot.adapters.rest.web.IAuthController;
import app.desafio.spring.boot.domain.model.User;
import app.desafio.spring.boot.domain.ports.IUserUseCase;
import app.desafio.spring.boot.infrastructure.security.JwtTokenUtil;
import app.desafio.spring.boot.infrastructure.util.PasswordUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "Autenticacion", description = "Operaciones relacionadas con autenticacion")
final class AuthControllerImpl implements IAuthController {

    private final IUserUseCase userUseCase;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthControllerImpl(IUserUseCase userUseCase, JwtTokenUtil jwtTokenUtil) {
        this.userUseCase = userUseCase;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> optionalUser = userUseCase.getUserByUsername(userDTO.username());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (PasswordUtil.matches(userDTO.password(), user.getPassword())) {
                    String token = jwtTokenUtil.generateToken(userDTO.username());
                    log.info("Generated JWT Token: " + token);
                    Map<String, String> responseMap = new HashMap<>();
                    responseMap.put("Bearer", token);
                    ApiResponse<Map<String, String>> response = new ApiResponse<>("OK", "Authentication successful", responseMap);
                    return ResponseEntity.ok(response);
                } else {
                    ApiResponse<String> response = new ApiResponse<>("NOK", "Invalid credentials", null);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } else {
                ApiResponse<String> response = new ApiResponse<>("NOK", "User not found", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error occurred during authentication: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}

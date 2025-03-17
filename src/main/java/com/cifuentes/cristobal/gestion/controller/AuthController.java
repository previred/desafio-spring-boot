package com.cifuentes.cristobal.gestion.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.cifuentes.cristobal.gestion.model.dto.JWTResponse;
import com.cifuentes.cristobal.gestion.model.dto.LoginRequest;
import com.cifuentes.cristobal.gestion.model.entity.User;
import com.cifuentes.cristobal.gestion.repository.UserRepository;
import com.cifuentes.cristobal.gestion.security.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	 private final UserRepository userRepository;


	public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
	}

	@Operation(summary = "Autenticación de usuario (login)", description = "Genera un token JWT para el usuario con email y contraseña válidos.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Login exitoso, devuelve token JWT"),
	    @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
	    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
	})
	@PostMapping("/login")
	public JWTResponse login(@RequestBody LoginRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (AuthenticationException e) {
			throw new RuntimeException("Invalid credentials");
		}

		  User user = userRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("User not found"));
		 String token = jwtUtil.generateToken(user.getEmail(), user.getId());
		return new JWTResponse(token);
	}
}

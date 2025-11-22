package com.yoserp1.tareas.controller;

import com.yoserp1.tareas.dto.JwtResponseDTO;
import com.yoserp1.tareas.dto.LoginRequestDTO;
import com.yoserp1.tareas.exception.AplicacionException;
import com.yoserp1.tareas.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
    name = "Autenticacion",
    description = "API para la gestión de credenciales y autenticación de usuarios")
public class AuthController {

  @Autowired private IAuthService authService;

  @Operation(
      summary = "Login de usuario",
      description = "Autentica un usuario y devuelve un token JWT")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso, token generado"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @PostMapping("/login")
  public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO request) {
    JwtResponseDTO response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @Operation(
      summary = "Obtener perfil del usuario autenticado",
      description = "Retorna la información del usuario actual")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
      })
  @GetMapping("/me")
  public ResponseEntity<LoginRequestDTO> perfil(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      throw AplicacionException.validation("No autenticado");
    }
    LoginRequestDTO usuario = authService.getPerfil(userDetails.getUsername());
    return ResponseEntity.ok(usuario);
  }
}

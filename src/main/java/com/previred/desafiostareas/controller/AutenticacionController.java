package com.previred.desafiostareas.controller;


import com.previred.desafiostareas.controller.dto.LoginRequest;
import com.previred.desafiostareas.controller.dto.LoginResponse;
import com.previred.desafiostareas.service.AutenticacionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de autenticación.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AutenticacionController {

    private final AutenticacionServiceImpl autenticacionServiceImpl;

    /**
     * POST login: Autenticación de usuarioEntity
     * Autentica un usuarioEntity y retorna un token JWT
     *
     * @param loginRequest  (required)
     * @return Autenticación exitosa (status code 200)
     *         or Credenciales inválidas (status code 401)
     *         or Solicitud inválida (status code 400)
     */
    @Operation(
            operationId = "login",
            summary = "Autenticación de usuarioEntity",
            description = "Autentica un usuarioEntity y retorna un token JWT",
            tags = { "Autenticacion" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = {
                            @Content(mediaType = "application/json", schema = @Schema())
                    }),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {
                            @Content(mediaType = "application/json", schema = @Schema())
                    })
            }
    )
    @PostMapping(
            value = "login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<LoginResponse> login(
             @Valid @RequestBody LoginRequest loginRequest){
        log.info(" login   email: {}", loginRequest.getEmail());
        LoginResponse response = autenticacionServiceImpl.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}

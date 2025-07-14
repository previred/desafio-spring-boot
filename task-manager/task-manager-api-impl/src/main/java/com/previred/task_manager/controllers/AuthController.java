package com.previred.task_manager.controllers;


import com.previred.task_manager.dto.base.ErrorResponseDto;
import com.previred.task_manager.dto.user.token.LoginReqDto;
import com.previred.task_manager.dto.user.token.LoginRespDto;
import com.previred.task_manager.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.previred.task_manager.components.constants.APIField.AUTH_API;

@RestController
@RequestMapping(AUTH_API)
@Validated
public class AuthController {
    private final IAuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para autenticar al usuario y generar un token JWT.
     * Recibe email y contraseña, actualiza el campo lastLogin y retorna el token.
     *
     * @param LoginReqDto Objeto que contiene email y password.
     * @return Objeto con el token JWT.
     */
    @Operation(
            summary = "Autenticar usuario y generar token JWT",
            description = "Valida las credenciales, actualiza lastLogin y retorna un token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa. Token generado.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginRespDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginRespDto> login(@Valid @RequestBody LoginReqDto reqDto) {
        log.info("AuthController.login {}",reqDto.email());
        return ResponseEntity.ok(authService.login(reqDto));
    }
}

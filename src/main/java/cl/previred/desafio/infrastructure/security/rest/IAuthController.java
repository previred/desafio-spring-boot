package cl.previred.desafio.infrastructure.security.rest;

import cl.previred.desafio.infrastructure.security.rest.dtos.AuthResponse;
import cl.previred.desafio.infrastructure.security.rest.dtos.InvalidAuthResponse;
import cl.previred.desafio.infrastructure.security.rest.dtos.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Auth",description = "Login")
public interface IAuthController {
    @Operation(
            summary = "Login",
            description = "Autenticación de usuario",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthRequest.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Datos reales",
                                            summary = "Login de para obtener token",
                                            description = "Credenciales válidas",
                                            value = """
                                {
                                  "username": "usuario1",
                                  "password": "admin"
                                }
                                """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Inicio de sesión exitoso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Error en la autenticación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidAuthResponse.class))
                    )
            }
    )
    void auth(AuthRequest authRequest);
}

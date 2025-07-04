package app.desafio.spring.boot.adapters.rest.web;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;


public interface IUserController {

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un usuario en el sistema con nombre y email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    ResponseEntity<?> createUser(UserDTO userDto);
}

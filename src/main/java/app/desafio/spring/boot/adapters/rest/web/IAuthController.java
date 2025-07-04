package app.desafio.spring.boot.adapters.rest.web;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface IAuthController {

    @Operation(
            summary = "Autenticar usuario",
            description = "Recibe las credenciales del usuario y devuelve un token JWT si la autenticación es exitosa."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, se devuelve el token"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "400", description = "Datos mal formateados")
    })
    ResponseEntity<?> authenticateUser(UserDTO userDTO);

}

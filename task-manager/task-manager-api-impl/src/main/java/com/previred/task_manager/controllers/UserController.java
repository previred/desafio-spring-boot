package com.previred.task_manager.controllers;


import com.previred.task_manager.dto.base.ErrorResponseDto;
import com.previred.task_manager.dto.user.UserReqDto;
import com.previred.task_manager.dto.user.UserRespDto;
import com.previred.task_manager.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


import static com.previred.task_manager.components.constants.APIField.USER_API;

@RestController
@RequestMapping(USER_API)
@Validated
public class UserController {
    private final IUserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar un nuevo usuario",
            description = "Registra un nuevo usuario, validando que el email no exista, la contraseña cumpla el patrón y que los teléfonos sean únicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRespDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos de entrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRespDto> saveUser(@Valid @RequestBody UserReqDto userReqDto) {
        log.info("UserController.saveUser {}",userReqDto.getEmail());
        UserRespDto response = userService.saveUser(userReqDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Recupera los datos del usuario especificado por su ID. Se requiere que el usuario esté autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado y retornado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserRespDto.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content =  @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserRespDto> getUserById(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Identificador único del usuario (UUID)",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable UUID id) {
        log.info("UserController.getUserById {}",id);

        return ResponseEntity.ok(userService.getUserById(id));
    }
}

package com.provired.challenge.controller;

import com.provired.challenge.dto.ResponseWrapper;
import com.provired.challenge.dto.UserDTO;
import com.provired.challenge.dto.UserRegistrationDTO;
import com.provired.challenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Gestión de usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados.")
    public ResponseEntity<ResponseWrapper<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Usuarios obtenidos", userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Obtiene un usuario específico.")
    public ResponseEntity<ResponseWrapper<UserDTO>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Usuario obtenido", userService.getUserById(id)));
    }

    @PostMapping
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario.")
    public ResponseEntity<ResponseWrapper<UserDTO>> createUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Usuario creado",
                userService.createUser(registrationDTO.getUsername(), registrationDTO.getPassword())));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Modifica los datos de un usuario.")
    public ResponseEntity<ResponseWrapper<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Usuario actualizado", userService.updateUser(id, userDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por ID.")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseWrapper<>(true, "Usuario eliminado", null));
    }
}

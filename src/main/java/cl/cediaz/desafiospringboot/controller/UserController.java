package cl.cediaz.desafiospringboot.controller;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserResponse;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.mapper.UserMapper;
import cl.cediaz.desafiospringboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operaciones relacionadas con usuarios")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Actualizar usuario existente",
            description = "Permite modificar username y rol. La contraseña es opcional: si se envía, se actualiza; si no, se mantiene la actual.",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UserUpdateRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Obtener usuario por su id",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Obtener usuario por su username",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @Operation(
            summary = "Eliminar usuario por su id",
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
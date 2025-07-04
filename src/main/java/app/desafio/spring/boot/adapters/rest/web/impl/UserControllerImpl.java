package app.desafio.spring.boot.adapters.rest.web.impl;

import app.desafio.spring.boot.adapters.rest.dto.UserDTO;
import app.desafio.spring.boot.adapters.rest.response.ApiResponse;
import app.desafio.spring.boot.adapters.rest.web.IUserController;
import app.desafio.spring.boot.domain.model.User;
import app.desafio.spring.boot.domain.ports.IUserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
final class UserControllerImpl implements IUserController {

    private final IUserUseCase userUseCase;

    UserControllerImpl(IUserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {
        try {
            userUseCase.createUser(userDto);
            ApiResponse<User> response = new ApiResponse<>("OK", "User created successfully", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error creating the user: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

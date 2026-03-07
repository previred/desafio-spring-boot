package cl.cediaz.desafiospringboot.mapper;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserResponse;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.Role;
import cl.cediaz.desafiospringboot.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRequest request, Role role) {
        return User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .fechaUltLogin(null)
                .build();
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().getName());
    }

    public User toEntity(UserUpdateRequest request, User existingUser, Role role) {
        existingUser.setUsername(request.username());
        existingUser.setRole(role);

        if (request.password() != null && !request.password().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.password()));
        }

        return existingUser;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}


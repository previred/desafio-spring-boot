package cl.cediaz.desafiospringboot.mapper;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserResponse;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.Role;
import cl.cediaz.desafiospringboot.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserMapperTest {

    private UserMapper mapper;
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new BCryptPasswordEncoder();
        mapper = new UserMapper(encoder);
    }

    @Test
    void testToEntityFromUserRequest() {
        UserRequest request = new UserRequest("newuser", "secret", "ROLE_USER");

        Role role = Role.builder().id(1L).name("ROLE_USER").build();

        User user = mapper.toEntity(request, role);

        assertEquals("newuser", user.getUsername());
        assertTrue(encoder.matches("secret", user.getPassword()));
        assertEquals("ROLE_USER", user.getRole().getName());
    }

    @Test
    void testToEntityFromUserUpdateRequestWithPassword() {
        User existing = User.builder()
                .id(1L)
                .username("olduser")
                .password(encoder.encode("oldpass"))
                .role(Role.builder().id(1L).name("ROLE_USER").build())
                .build();

        UserUpdateRequest request = new UserUpdateRequest(
                "updateduser",
                2L,
                "newpass");

        Role newRole = Role.builder().id(2L).name("ROLE_ADMIN").build();

        User updated = mapper.toEntity(request, existing, newRole);

        assertEquals("updateduser", updated.getUsername());
        assertEquals("ROLE_ADMIN", updated.getRole().getName());
        assertTrue(encoder.matches("newpass", updated.getPassword()));
    }

    @Test
    void testToEntityFromUserUpdateRequestWithoutPassword() {
        User existing = User.builder()
                .id(1L)
                .username("olduser")
                .password(encoder.encode("oldpass"))
                .role(Role.builder().id(1L).name("ROLE_USER").build())
                .build();

        UserUpdateRequest request = new UserUpdateRequest(
                "updateduser",
                2L,
                null);

        Role newRole = Role.builder().id(2L).name("ROLE_ADMIN").build();

        User updated = mapper.toEntity(request, existing, newRole);

        assertEquals("updateduser", updated.getUsername());
        assertEquals("ROLE_ADMIN", updated.getRole().getName());
        assertTrue(encoder.matches("oldpass", updated.getPassword()));
    }

    @Test
    void testToResponse() {
        User user = User.builder()
                .id(1L)
                .username("responseUser")
                .role(Role.builder().id(1L).name("ROLE_USER").build())
                .build();

        UserResponse response = mapper.toResponse(user);

        assertEquals(1L, response.id());
        assertEquals("responseUser", response.username());
        assertEquals("ROLE_USER", response.role());
    }
}
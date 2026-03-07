package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.dto.UserRequest;
import cl.cediaz.desafiospringboot.dto.UserUpdateRequest;
import cl.cediaz.desafiospringboot.entity.Role;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.mapper.UserMapper;
import cl.cediaz.desafiospringboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest("newuser", "secret", "ROLE_USER");

        Role role = Role.builder().id(1L).name("ROLE_USER").build();
        User user = User.builder().id(1L).username("newuser").password("encoded").role(role).build();

        when(roleService.findByName(anyString())).thenReturn(role);
        when(userMapper.toEntity(any(UserRequest.class), any(Role.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.createUser(request);

        assertEquals("newuser", created.getUsername());
        assertEquals("ROLE_USER", created.getRole().getName());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUserWithPassword() {
        User existing = User.builder()
                .id(1L)
                .username("olduser")
                .password("oldpass")
                .role(Role.builder().id(1L).name("ROLE_USER").build())
                .build();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("updateduser")
                .roleId(2L)
                .password("newpass")
                .build();

        Role newRole = Role.builder().id(2L).name("ROLE_ADMIN").build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(roleService.findById(anyLong())).thenReturn(newRole);
        when(userMapper.toEntity(any(UserUpdateRequest.class), any(User.class), any(Role.class)))
                .thenAnswer(inv -> {
                    UserUpdateRequest req = inv.getArgument(0);
                    User ex = inv.getArgument(1);
                    Role role = inv.getArgument(2);
                    return User.builder()
                            .id(ex.getId())
                            .username(req.getUsername())
                            .password(req.getPassword())
                            .role(role)
                            .build();
                });
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = userService.updateUser(1L, request);

        assertEquals("updateduser", updated.getUsername());
        assertEquals("ROLE_ADMIN", updated.getRole().getName());
        assertEquals("newpass", updated.getPassword());
    }


    @Test
    void testUpdateUserWithoutPassword() {
        User existing = User.builder()
                .id(1L)
                .username("olduser")
                .password("oldpass")
                .role(Role.builder().id(1L).name("ROLE_USER").build())
                .build();

        UserUpdateRequest request = UserUpdateRequest.builder()
                .username("updateduser")
                .roleId(2L)
                .build();

        Role newRole = Role.builder().id(2L).name("ROLE_ADMIN").build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(roleService.findById(anyLong())).thenReturn(newRole);
        when(userMapper.toEntity(any(UserUpdateRequest.class), any(User.class), any(Role.class)))
                .thenAnswer(inv -> {
                    UserUpdateRequest req = inv.getArgument(0);
                    User ex = inv.getArgument(1);
                    Role role = inv.getArgument(2);
                    return User.builder()
                            .id(ex.getId())
                            .username(req.getUsername())
                            .password(ex.getPassword())
                            .role(role)
                            .build();
                });
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = userService.updateUser(1L, request);

        assertEquals("updateduser", updated.getUsername());
        assertEquals("ROLE_ADMIN", updated.getRole().getName());
        assertEquals("oldpass", updated.getPassword());
    }

}

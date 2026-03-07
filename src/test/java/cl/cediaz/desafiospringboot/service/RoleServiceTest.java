package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.entity.Role;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void testFindByNameSuccess() {
        Role role = Role.builder().id(1L).name("ROLE_USER").build();
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));

        Role found = roleService.findByName("ROLE_USER");

        assertEquals("ROLE_USER", found.getName());
    }

    @Test
    void testFindByNameNotFound() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.findByName("ROLE_UNKNOWN"));
    }

    @Test
    void testFindByIdSuccess() {
        Role role = Role.builder().id(2L).name("ROLE_ADMIN").build();
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        Role found = roleService.findById(2L);

        assertEquals("ROLE_ADMIN", found.getName());
    }

    @Test
    void testFindByIdNotFound() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.findById(99L));
    }
}
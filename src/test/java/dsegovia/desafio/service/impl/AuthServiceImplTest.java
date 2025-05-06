package dsegovia.desafio.service.impl;

import dsegovia.desafio.entity.Usuario;
import dsegovia.desafio.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
    }

    @Test
    void findByUsername_WhenUserExists_ReturnsUser() {
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        Usuario result = authService.findByUsername("testuser");

        assertEquals(usuario, result);
    }

    @Test
    void findByUsername_WhenUserDoesNotExist_ReturnsNull() {
        when(usuarioRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Usuario result = authService.findByUsername("nonexistent");

        assertNull(result);
    }
}
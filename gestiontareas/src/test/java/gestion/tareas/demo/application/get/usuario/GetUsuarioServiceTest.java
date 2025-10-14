package gestion.tareas.demo.application.get.usuario;

import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import gestion.tareas.demo.infrastructure.db.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private GetUsuarioService getUsuarioService;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        getUsuarioService = new GetUsuarioService(usuarioRepository);

        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("bernardo");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("valeska");
    }

    @Test
    void getUsuarioServiceTest1() {
        when(usuarioRepository.findByUsername("bernardo")).thenReturn(Optional.of(usuario1));

        Optional<Usuario> response = getUsuarioService.findByUsername("bernardo");

        assertTrue(response.isPresent());
        assertEquals(1L, response.get().getId());
        assertEquals("bernardo", response.get().getUsername());

        verify(usuarioRepository, times(1)).findByUsername("bernardo");
    }

    @Test
    void getUsuarioServiceTest2() {
        when(usuarioRepository.findByUsername("desconocido")).thenReturn(Optional.empty());

        Optional<Usuario> response = getUsuarioService.findByUsername("desconocido");

        assertFalse(response.isPresent());
        verify(usuarioRepository, times(1)).findByUsername("desconocido");
    }
}

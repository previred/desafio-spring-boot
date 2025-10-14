package gestion.tareas.demo.application.get.auth;

import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAuthServiceTest {

    @Mock
    private GetUsuarioGateway getUsuarioGateway;

    @Mock
    private SecurityContext securityContext;


    @InjectMocks
    private GetAuthService getAuthService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        getAuthService = new GetAuthService(getUsuarioGateway);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAuthServiceTest1() {
        String username = "bernardo";
        TestingAuthenticationToken authentication =
                new TestingAuthenticationToken(username, "password", "ADMIN");
        authentication.setAuthenticated(true);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername(username);
        usuario.setRole("ADMIN");

        when(getUsuarioGateway.findByUsername(username)).thenReturn(Optional.of(usuario));

        Usuario result = getAuthService.obtenerUsuarioAutenticado();

        assertEquals(username, result.getUsername());
        assertEquals("ADMIN", result.getRole());
        verify(getUsuarioGateway, times(1)).findByUsername(username);

        getAuthService.obtenerUsuarioAutenticado();
    }
}

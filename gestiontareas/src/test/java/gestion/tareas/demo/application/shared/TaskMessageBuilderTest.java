package gestion.tareas.demo.application.shared;

import gestion.tareas.demo.application.get.auth.GetAuthGateway;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskMessageBuilderTest {
    @Mock
    private GetAuthGateway getAuthGateway;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TaskMessageBuilder taskMessageBuilder;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setUsername("bernardo");
        when(getAuthGateway.obtenerUsuarioAutenticado()).thenReturn(usuario);
    }

    @Test
    void taskMessageBuilderTest1() {
        when(request.getMethod()).thenReturn("POST");

        StateAppDto response = taskMessageBuilder.buildMessage(10L, true);

        assertNotNull(response);
        assertEquals("Se registró exitosamente la tarea 10", response.getMessage());
        assertEquals("bernardo", response.getLoggedUser());

        verify(request, times(1)).getMethod();
        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
    }

    @Test
    void taskMessageBuilderTest2() {
        when(request.getMethod()).thenReturn("PUT");

        StateAppDto response = taskMessageBuilder.buildMessage(20L, true);

        assertNotNull(response);
        assertEquals("Se actualizó exitosamente la tarea 20", response.getMessage());
        assertEquals("bernardo", response.getLoggedUser());

        verify(request, times(1)).getMethod();
        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
    }

    @Test
    void taskMessageBuilderTest3() {
        when(request.getMethod()).thenReturn("DELETE");

        StateAppDto response = taskMessageBuilder.buildMessage(30L, true);

        assertNotNull(response);
        assertEquals("Se eliminó exitosamente la tarea 30", response.getMessage());
        assertEquals("bernardo", response.getLoggedUser());

        verify(request, times(1)).getMethod();
        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
    }

    @Test
    void taskMessageBuilderTest4() {
        when(request.getMethod()).thenReturn("PATCH");

        StateAppDto response = taskMessageBuilder.buildMessage(40L, true);

        assertNotNull(response);
        assertEquals("Operación exitosa sobre la tarea 40", response.getMessage());
        assertEquals("bernardo", response.getLoggedUser());

        verify(request, times(1)).getMethod();
        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
    }

    @Test
    void taskMessageBuilderTest5() {
        when(request.getMethod()).thenReturn("POST");

        StateAppDto response = taskMessageBuilder.buildMessage(50L, false);

        assertNotNull(response);
        assertEquals("No existe Tarea 50", response.getMessage());
        assertEquals("bernardo", response.getLoggedUser());

        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
    }
}

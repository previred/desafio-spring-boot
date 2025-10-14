package gestion.tareas.demo.application.post;

import gestion.tareas.demo.application.get.auth.GetAuthGateway;
import gestion.tareas.demo.application.get.estadotarea.GetEstadoTareaGateway;
import gestion.tareas.demo.application.shared.TaskMessageBuilder;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.db.TareaRequestDto;
import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostTareaServiceTest {
    @Mock
    private GetAuthGateway getAuthGateway;

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private GetEstadoTareaGateway getEstadoTareaGateway;

    @Mock
    private TaskMessageBuilder messageBuilder;

    @InjectMocks
    private PostTareaService postTareaService;

    private TareaRequestDto tareaRequestDto;
    private EstadoTarea estadoTarea;
    private Usuario usuario;
    private StateAppDto stateAppDto;

    @BeforeEach
    void setUp() {
        postTareaService = new PostTareaService(getAuthGateway, tareaRepository, getEstadoTareaGateway, messageBuilder);

        usuario = new Usuario();
        usuario.setId(10L);
        usuario.setUsername("bernardo");
        usuario.setPassword("1234");
        usuario.setRole("USER");

        estadoTarea = new EstadoTarea();
        estadoTarea.setId(1L);
        estadoTarea.setNombre("Pendiente");

        tareaRequestDto = new TareaRequestDto();
        tareaRequestDto.setDescripcion("Tarea de prueba");
        tareaRequestDto.setExpirationDate(OffsetDateTime.now().plusDays(1));
        tareaRequestDto.setState(TareaRequestDto.StateEnum.fromValue(1L));

        stateAppDto = new StateAppDto();
        stateAppDto.setMessage("Tarea creada correctamente");
    }

    @Test
    void postTareaServiceTest1() {
        when(getAuthGateway.obtenerUsuarioAutenticado()).thenReturn(usuario);
        when(getEstadoTareaGateway.getEstadoTareaById(1L)).thenReturn(estadoTarea);

        // Simular que al guardar la tarea se le asigna ID 99
        doAnswer(invocation -> {
            Tareas t = invocation.getArgument(0);
            t.setId(99L);
            return t;
        }).when(tareaRepository).save(any(Tareas.class));

        when(messageBuilder.buildMessage(99L, true)).thenReturn(stateAppDto);

        ResponseEntity<StateAppDto> response = postTareaService.tareasPost(tareaRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Tarea creada correctamente", response.getBody().getMessage());

        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
        verify(getEstadoTareaGateway, times(1)).getEstadoTareaById(1L);
        verify(tareaRepository, times(1)).save(any(Tareas.class));
        verify(messageBuilder, times(1)).buildMessage(99L, true);
    }
}

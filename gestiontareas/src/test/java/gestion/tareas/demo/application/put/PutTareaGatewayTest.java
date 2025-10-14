package gestion.tareas.demo.application.put;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PutTareaGatewayTest {
    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private GetAuthGateway getAuthGateway;

    @Mock
    private GetEstadoTareaGateway getEstadoTareaGateway;

    @Mock
    private TaskMessageBuilder messageBuilder;

    @InjectMocks
    private PutTareaGateway putTareaGateway;

    @InjectMocks
    private PutTareaService putTareaService;

    private Tareas tarea;
    private EstadoTarea estadoTarea;
    private Usuario usuario;
    private TareaRequestDto tareaRequestDto;
    private StateAppDto stateAppDto;

    @BeforeEach
    void setUp() {
        putTareaGateway = new PutTareaGateway(putTareaService);
        putTareaService = new PutTareaService(tareaRepository, getAuthGateway, getEstadoTareaGateway, messageBuilder);

        tarea = new Tareas();
        tarea.setId(1L);
        tarea.setDescripcion("Tarea original");

        estadoTarea = new EstadoTarea();
        estadoTarea.setId(2L);
        estadoTarea.setNombre("En Progreso");

        usuario = new Usuario();
        usuario.setId(10L);

        tareaRequestDto = new TareaRequestDto();
        tareaRequestDto.setDescripcion("Tarea modificada");
        tareaRequestDto.setExpirationDate(OffsetDateTime.now().plusDays(1));
        tareaRequestDto.setState(TareaRequestDto.StateEnum.NUMBER_2);

        stateAppDto = new StateAppDto();
        stateAppDto.setMessage("Actualizada correctamente");
    }

    @Test
    void putTareaGatewayTest1() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(getAuthGateway.obtenerUsuarioAutenticado()).thenReturn(usuario);
        when(getEstadoTareaGateway.getEstadoTareaById(2L)).thenReturn(estadoTarea);
        when(messageBuilder.buildMessage(1L, true)).thenReturn(stateAppDto);

        ResponseEntity<StateAppDto> response = putTareaGateway.tareasIdPut(1L, tareaRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Actualizada correctamente", response.getBody().getMessage());
        assertEquals("Tarea modificada", tarea.getDescripcion());
        assertEquals(usuario.getId(), tarea.getUsuarioId());
        assertEquals(estadoTarea, tarea.getEstado());

        verify(tareaRepository, times(1)).findById(1L);
        verify(getAuthGateway, times(1)).obtenerUsuarioAutenticado();
        verify(getEstadoTareaGateway, times(1)).getEstadoTareaById(2L);
        verify(tareaRepository, times(1)).save(tarea);
        verify(messageBuilder, times(1)).buildMessage(1L, true);
    }

    @Test
    void putTareaGatewayTest2() {
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());
        when(messageBuilder.buildMessage(99L, false)).thenReturn(stateAppDto);

        ResponseEntity<StateAppDto> response = putTareaGateway.tareasIdPut(99L, tareaRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(tareaRepository, times(1)).findById(99L);
        verify(tareaRepository, never()).save(any(Tareas.class));
        verify(messageBuilder, times(1)).buildMessage(99L, false);
    }
}

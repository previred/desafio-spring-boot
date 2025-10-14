package gestion.tareas.demo.application.del;


import gestion.tareas.demo.application.shared.TaskMessageBuilder;
import gestion.tareas.demo.db.StateAppDto;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DelTareaGatewayTest {
    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private TaskMessageBuilder messageBuilder;

    @InjectMocks
    private DelTareaGateway delTareaGateway;

    @InjectMocks
    private DelTareaService delTareaService;

    private StateAppDto stateAppDto;

    @BeforeEach
    void setUp() {
        delTareaGateway = new DelTareaGateway(delTareaService);
        delTareaService = new DelTareaService(tareaRepository, messageBuilder);

        stateAppDto = new StateAppDto();
        stateAppDto.setMessage("Tarea procesada correctamente");
    }

    @Test
    void delTareaGatewayTest1() {
        Long tareaId = 1L;

        when(tareaRepository.existsById(tareaId)).thenReturn(true);
        when(messageBuilder.buildMessage(tareaId, true)).thenReturn(stateAppDto);

        ResponseEntity<StateAppDto> response = delTareaGateway.tareasIdDelete(tareaId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Tarea procesada correctamente", response.getBody().getMessage());

        verify(tareaRepository, times(1)).existsById(tareaId);
        verify(tareaRepository, times(1)).deleteById(tareaId);
        verify(messageBuilder, times(1)).buildMessage(tareaId, true);
    }

    @Test
    void delTareaGatewayTest2() {
        Long tareaId = 99L;

        when(tareaRepository.existsById(tareaId)).thenReturn(false);
        when(messageBuilder.buildMessage(tareaId, false)).thenReturn(stateAppDto);

        ResponseEntity<StateAppDto> response = delTareaGateway.tareasIdDelete(tareaId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(tareaRepository, times(1)).existsById(tareaId);
        verify(tareaRepository, never()).deleteById(anyLong());
        verify(messageBuilder, times(1)).buildMessage(tareaId, false);
    }
}

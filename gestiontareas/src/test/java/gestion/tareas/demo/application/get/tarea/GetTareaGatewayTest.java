package gestion.tareas.demo.application.get.tarea;

import gestion.tareas.demo.application.get.tarea.mapper.GetTareaMapper;
import gestion.tareas.demo.db.TareaDto;
import gestion.tareas.demo.infrastructure.db.entity.Tareas;
import gestion.tareas.demo.infrastructure.db.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetTareaGatewayTest {
    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private GetTareaMapper getTareaMapper;

    @InjectMocks
    private GetTareaService getTareaService;

    @InjectMocks
    private GetTareaGateway getTareaGateway;


    private Tareas tarea1;
    private Tareas tarea2;
    private TareaDto tareaDto1;
    private TareaDto tareaDto2;

    @BeforeEach
    void setUp() {
        getTareaGateway = new GetTareaGateway(getTareaService);
        getTareaService = new GetTareaService(tareaRepository, getTareaMapper);

        tarea1 = new Tareas();
        tarea1.setId(1L);
        tarea1.setDescripcion("Tarea 1");

        tarea2 = new Tareas();
        tarea2.setId(2L);
        tarea2.setDescripcion("Tarea 2");

        tareaDto1 = new TareaDto();
        tareaDto1.setId(1L);
        tareaDto1.setDescripcion("Tarea 1");

        tareaDto2 = new TareaDto();
        tareaDto2.setId(2L);
        tareaDto2.setDescripcion("Tarea 2");
    }

    @Test
    void getTareaGatewayTest1() {
        when(tareaRepository.findByUsuarioId(10L)).thenReturn(Arrays.asList(tarea1, tarea2));
        when(getTareaMapper.toDto(tarea1)).thenReturn(tareaDto1);
        when(getTareaMapper.toDto(tarea2)).thenReturn(tareaDto2);

        ResponseEntity<List<TareaDto>> response = getTareaGateway.tareasGet(10);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Tarea 1", response.getBody().get(0).getDescripcion());
        assertEquals("Tarea 2", response.getBody().get(1).getDescripcion());

        verify(tareaRepository, times(1)).findByUsuarioId(10L);
        verify(getTareaMapper, times(2)).toDto(any(Tareas.class));
    }

    @Test
    void getTareaGatewayTest2() {
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea1));
        when(getTareaMapper.toDto(tarea1)).thenReturn(tareaDto1);

        ResponseEntity<TareaDto> response = getTareaGateway.tareasIdGet(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Tarea 1", response.getBody().getDescripcion());

        verify(tareaRepository, times(1)).findById(1L);
        verify(getTareaMapper, times(1)).toDto(tarea1);
    }

    @Test
    void getTareaGatewayTest3() {
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<TareaDto> response = getTareaGateway.tareasIdGet(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(tareaRepository, times(1)).findById(99L);
        verify(getTareaMapper, never()).toDto(any());
    }

}

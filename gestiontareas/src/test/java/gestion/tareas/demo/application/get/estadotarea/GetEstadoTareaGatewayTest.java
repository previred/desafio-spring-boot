package gestion.tareas.demo.application.get.estadotarea;

import gestion.tareas.demo.infrastructure.db.entity.EstadoTarea;
import gestion.tareas.demo.infrastructure.db.repository.EstadoTareaRepository;
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
public class GetEstadoTareaGatewayTest {
    @Mock
    private EstadoTareaRepository estadoTareaRepository;

    @InjectMocks
    private GetEstadoTareaService getEstadoTareaService;

    @InjectMocks
    private GetEstadoTareaGateway getEstadoTareaGateway;

    private EstadoTarea mockEstadoTarea;


    @BeforeEach
    void setUp() {
        getEstadoTareaGateway = new GetEstadoTareaGateway(getEstadoTareaService);
        getEstadoTareaService = new GetEstadoTareaService(estadoTareaRepository);

        mockEstadoTarea = new EstadoTarea();
        mockEstadoTarea.setId(1L);
        mockEstadoTarea.setNombre("En Progreso");
    }

    @Test
    void getEstadoTareaGatewayTest1() {
        when(estadoTareaRepository.findById(1L)).thenReturn(Optional.of(mockEstadoTarea));

        EstadoTarea result = getEstadoTareaGateway.getEstadoTareaById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("En Progreso", result.getNombre());
        verify(estadoTareaRepository, times(1)).findById(1L);
    }

    @Test
    void getEstadoTareaGatewayTest2() {
        when(estadoTareaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> getEstadoTareaGateway.getEstadoTareaById(99L));

        assertEquals("Estado de tarea no encontrado con id: 99", ex.getMessage());
        verify(estadoTareaRepository, times(1)).findById(99L);
    }
}

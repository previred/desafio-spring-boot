package dsegovia.desafio.service.impl;

import dsegovia.desafio.entity.EstadoTarea;
import dsegovia.desafio.repository.EstadoTareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstadoTareaServiceImplTest {

    @Mock
    private EstadoTareaRepository estadoTareaRepository;

    @InjectMocks
    private EstadoTareaServiceImpl estadoTareaService;

    private List<EstadoTarea> estadoTareas;

    @BeforeEach
    void setUp() {
        EstadoTarea estado1 = new EstadoTarea();
        estado1.setId(1L);
        estado1.setNombre("PENDIENTE");

        EstadoTarea estado2 = new EstadoTarea();
        estado2.setId(2L);
        estado2.setNombre("EN_PROGRESO");

        EstadoTarea estado3 = new EstadoTarea();
        estado3.setId(3L);
        estado3.setNombre("COMPLETADA");

        estadoTareas = Arrays.asList(estado1, estado2, estado3);
    }

    @Test
    void listarTodos_ReturnsAllEstados() {
        when(estadoTareaRepository.findAll()).thenReturn(estadoTareas);

        List<EstadoTarea> result = estadoTareaService.listarTodos();

        assertEquals(3, result.size());
        assertEquals(estadoTareas, result);
        verify(estadoTareaRepository, times(1)).findAll();
    }
}
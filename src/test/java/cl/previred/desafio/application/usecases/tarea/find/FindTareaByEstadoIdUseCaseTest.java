package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.mocks.EstadoTareaMock;
import cl.previred.desafio.mocks.TareaMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindTareaByEstadoIdUseCaseTest {

    @Mock
    private IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    @Mock
    private ITareaRepository tareaRepository;
    @InjectMocks
    private FindTareaByEstadoIdUseCase findTareaByEstadoIdUseCase;

    @Test
    @DisplayName("Buscar tareas por estado")
    void execute() {
        when(findEstadoTareaByIdUseCase.execute(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        when(tareaRepository.findByEstadoId(1L)).thenReturn(TareaMock.tareasTipo2);
        var result = findTareaByEstadoIdUseCase.execute(1L);
        assertNotNull(result);
        assertEquals(2, result.size());
    }


}
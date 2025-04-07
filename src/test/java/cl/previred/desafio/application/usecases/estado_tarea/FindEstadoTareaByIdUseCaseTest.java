package cl.previred.desafio.application.usecases.estado_tarea;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.ports.out.repositories.IEstadoTareaRepository;
import cl.previred.desafio.mocks.EstadoTareaMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindEstadoTareaByIdUseCaseTest {
    @Mock
    private IEstadoTareaRepository repository;
    @InjectMocks
    private FindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;

    @Test
    @DisplayName("Buscar estado de tarea por id: [exitoso]")
    void execute() {
        when(repository.findById(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        var estadoTarea = findEstadoTareaByIdUseCase.execute(1L);
        assertNotNull(estadoTarea);
    }

    @Test
    @DisplayName("Buscar estado de tarea por id: [no existe]")
    void executeFail() {
        when(repository.findById(2L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> {
            findEstadoTareaByIdUseCase.execute(2L);
                }
        );
    }

}
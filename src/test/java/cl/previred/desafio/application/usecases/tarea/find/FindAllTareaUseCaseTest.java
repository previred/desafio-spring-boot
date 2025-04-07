package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
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
class FindAllTareaUseCaseTest {

    @Mock
    private ITareaRepository repository;
    @InjectMocks
    private FindAllTareaUseCase findAllTareaUseCase;

    @Test
    @DisplayName("Buscar todas las tareas")
    void execute() {
        when(repository.findAll()).thenReturn(TareaMock.tareas);
        var tareas = findAllTareaUseCase.execute();
        assertAll(
                ()-> assertNotNull(tareas,"No se encontraron tareas"),
                ()-> assertFalse(tareas.isEmpty(),"Se esperaban tareas"),
                () -> assertEquals(3, tareas.size(),"Se esperaban 3 tareas")
        );
    }
}
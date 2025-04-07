package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.mocks.TareaMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTareaUseCaseTest {
    @Mock
    private IFindTareaByIdUseCase findTareaByIdUseCase;
    @Mock
    private ITareaRepository tareaRepository;
    @InjectMocks
    private DeleteTareaUseCase deleteTareaUseCase;


    @Test
    @DisplayName("Eliminar tarea con id: [exitoso]")
    void execute() {
        when(findTareaByIdUseCase.execute(1L)).thenReturn(TareaMock.tarea1);
        doNothing().when(tareaRepository).deleteById(1L);
        deleteTareaUseCase.execute(1L);
    }

    @Test
    @DisplayName("Eliminar tarea con id: [fallido]")
    void executeFail() {
        when(findTareaByIdUseCase.execute(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> deleteTareaUseCase.execute(1L));
        verify(tareaRepository, never()).deleteById(1L);
    }
}
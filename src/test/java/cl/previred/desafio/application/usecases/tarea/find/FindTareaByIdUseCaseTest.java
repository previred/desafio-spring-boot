package cl.previred.desafio.application.usecases.tarea.find;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
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
class FindTareaByIdUseCaseTest {

    @Mock
    private ITareaRepository repository;
    @InjectMocks
    private FindTareaByIdUseCase findTareaByIdUseCase;

    @Test
    @DisplayName("Buscar Tarea por id: [exitoso]")
    void execute() {
        when(repository.findById(1L)).thenReturn(TareaMock.tarea1);
        var tarea = findTareaByIdUseCase.execute(1L);
        assertNotNull(tarea);
    }
    @Test
    @DisplayName("Buscar Tarea por id: [no existe]")
    void executeNotFound() {
        when(repository.findById(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()->{
            findTareaByIdUseCase.execute(1L);
        });
    }
}
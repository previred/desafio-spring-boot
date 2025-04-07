package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.mocks.TareaMock;
import cl.previred.desafio.mocks.UsuarioMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignTareaUseCaseTest {

    @Mock
    IFindTareaByIdUseCase findTareaByIdUseCase;
    @Mock
    IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    @Mock
    ITareaRepository repository;
    @InjectMocks
    AssignTareaUseCase assignTareaUseCase;

    @Test
    @DisplayName("Asignación de tarea a usuario: [exitoso]")
    void execute() {
        when(findUsuarioByIdUseCase.execute(2L)).thenReturn(UsuarioMock.usuario2);
        when(findTareaByIdUseCase.execute(1L)).thenReturn(TareaMock.tarea1);
        when(repository.save(TareaMock.tarea1)).thenReturn(TareaMock.tarea1);
        assignTareaUseCase.execute(1L, 2L);
        assertEquals(TareaMock.tarea1.getUsuarioAsignado(), UsuarioMock.usuario2);
    }
}
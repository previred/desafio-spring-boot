package cl.previred.desafio.application.usecases.usuario;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.ports.out.repositories.IUsuarioRepository;
import cl.previred.desafio.mocks.UsuarioMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUsuarioByIdUseCaseTest {

    @Mock
    private IUsuarioRepository repository;

    @InjectMocks
    private FindUsuarioByIdUseCase findUsuarioByIdUseCase;

    @Test
    @DisplayName("Buscar usuario por id: [exitoso]")
    void execute() {
        when(repository.findById(1L)).thenReturn(UsuarioMock.usuario1);
        var usuario = findUsuarioByIdUseCase.execute(1L);
        assertNotNull(usuario);
    }

    @Test
    @DisplayName("Buscar usuario por id: [no existe]")
    void executeFail() {
        when(repository.findById(2L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class,()->{
            findUsuarioByIdUseCase.execute(2L);
        });
    }
}
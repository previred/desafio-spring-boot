package cl.previred.desafio.application.usecases.usuario;

import cl.previred.desafio.domain.exceptions.UsernameNotFoundException;
import cl.previred.desafio.domain.ports.out.repositories.IUsuarioRepository;
import cl.previred.desafio.mocks.UsuarioMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUsuarioByUsernameUseCaseTest {

    @Mock
    private IUsuarioRepository repository;

    @InjectMocks
    private FindUsuarioByUsernameUseCase findUsuarioByUsernameUseCase;

    @DisplayName("Buscar usuario por username: [exitoso]")
    @Test
    void execute() {
        when(repository.findByUsername(anyString())).thenReturn(UsuarioMock.usuario1);
        var usuario = findUsuarioByUsernameUseCase.execute("usuario1");
        assertNotNull(usuario);
    }

    @DisplayName("Buscar usuario por username: [no existe]")
    @Test
    void executeFail() {
        when(repository.findByUsername("usuario2")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> {
            findUsuarioByUsernameUseCase.execute("usuario2");
        });
    }
}
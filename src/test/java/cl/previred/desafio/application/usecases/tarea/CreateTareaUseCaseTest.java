package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByUsernameUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.mocks.EstadoTareaMock;
import cl.previred.desafio.mocks.TareaMock;
import cl.previred.desafio.mocks.UsuarioMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTareaUseCaseTest {
    @Mock
    private ITareaRepository repository;
    @Mock
    private IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    @Mock
    private IFindUsuarioByUsernameUseCase findUsuarioByUsernameUseCase;
    @Mock
    private IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    @InjectMocks
    private CreateTareaUseCase createTareaUseCase;

    //Restablece el mock
    @BeforeEach
    void setUp() {
        TareaMock.clearTarea1();
    }

    @DisplayName("Crear Tarea con todos sus datos")
    @Test
    void execute() {
        when(findUsuarioByUsernameUseCase.execute("admin")).thenReturn(UsuarioMock.usuario1);
        when(findEstadoTareaByIdUseCase.execute(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        when(findUsuarioByIdUseCase.execute(1L)).thenReturn(UsuarioMock.usuario1);
        when(repository.save(TareaMock.tareaWithUserAssign)).thenReturn(TareaMock.tareaWithUserAssign);

        var tarea = createTareaUseCase.execute(TareaMock.tareaWithUserAssign,"admin");
        assertNotNull(tarea);
        assertEquals(TareaMock.tareaWithUserAssign.getId(), tarea.getId());
    }

    @DisplayName("Crear Tarea sin usuario asignado")
    @Test
    void executewhithOutUserAssign() {
        when(findUsuarioByUsernameUseCase.execute(anyString())).thenReturn(UsuarioMock.usuario1);
        when(findEstadoTareaByIdUseCase.execute(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        when(repository.save(TareaMock.tarea1)).thenReturn(TareaMock.tarea1);

        var tarea = createTareaUseCase.execute(TareaMock.tarea1,"admin");
        assertAll(
                () -> assertNotNull(tarea,"tarea retornada no puede ser nula"),
                () -> assertEquals(TareaMock.tarea1.getId(), tarea.getId(),"Tarea retornada no es igual"),
                () -> assertNull(tarea.getUsuarioAsignado(),"Usuario asignado debe ser nulo"),
                () -> assertNull(tarea.getFechaAsignacion(),"Fecha asignacion debe ser nula"),
                () -> assertEquals(UsuarioMock.usuario1,tarea.getUsuarioCreador(),"Usuario creador no es el correcto")
        );
        verify(findUsuarioByIdUseCase,never()).execute(anyLong());
    }

    @DisplayName("Crear Tarea sin estado asignado")
    @Test
    void executewhithOutState() {
        Tarea tareaWithOutState=Tarea.builder()
                .id(5L)
                .nombre("Tarea 5")
                .description("Descripcion de la tarea 5")
                .fechaCreacion(LocalDateTime.parse("2023-10-03T10:00:00"))
                .usuarioCreador(UsuarioMock.usuario1)
                .usuarioAsignado(UsuarioMock.usuario1)
                .fechaAsignacion(LocalDateTime.now())
                .build();


        when(findUsuarioByUsernameUseCase.execute(anyString())).thenReturn(UsuarioMock.usuario1);
        when(findUsuarioByIdUseCase.execute(1L)).thenReturn(UsuarioMock.usuario1);
        when(findEstadoTareaByIdUseCase.execute(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        when(repository.save(tareaWithOutState)).thenReturn(tareaWithOutState);

        createTareaUseCase.execute(tareaWithOutState,"admin");
        assertAll(
                () -> assertNotNull(tareaWithOutState,"tarea retornada no puede ser nula"),
                () -> assertNotNull(tareaWithOutState.getEstado(),"Estado no puede ser nulo, debe ser 1 por defecto"),
                () -> assertEquals(1L,tareaWithOutState.getEstado().getId(),"Estado no es el correcto")
        );
    }


    @DisplayName("Crear Tarea con estado asignado sin id")
    @Test
    void executewhithOutStateWhitOutId() {
        Tarea tareaWithOutState=Tarea.builder()
                .id(5L)
                .nombre("Tarea 5")
                .description("Descripcion de la tarea 5")
                .fechaCreacion(LocalDateTime.parse("2023-10-03T10:00:00"))
                .usuarioCreador(UsuarioMock.usuario1)
                .usuarioAsignado(UsuarioMock.usuario1)
                .fechaAsignacion(LocalDateTime.now())
                .estado(new EstadoTarea())
                .build();

        when(findUsuarioByUsernameUseCase.execute(anyString())).thenReturn(UsuarioMock.usuario1);
        when(findUsuarioByIdUseCase.execute(1L)).thenReturn(UsuarioMock.usuario1);
        when(findEstadoTareaByIdUseCase.execute(1L)).thenReturn(EstadoTareaMock.estadoTarea1);
        when(repository.save(tareaWithOutState)).thenReturn(tareaWithOutState);

        createTareaUseCase.execute(tareaWithOutState,"admin");
        assertAll(
                () -> assertNotNull(tareaWithOutState,"tarea retornada no puede ser nula"),
                () -> assertNotNull(tareaWithOutState.getEstado(),"Estado no puede ser nulo, debe ser 1 por defecto"),
                () -> assertEquals(1L,tareaWithOutState.getEstado().getId(),"Estado no es el correcto")
        );
    }


}
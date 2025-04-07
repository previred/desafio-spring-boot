package cl.previred.desafio.application.usecases.tarea;

import cl.previred.desafio.domain.models.EstadoTarea;
import cl.previred.desafio.domain.models.Tarea;
import cl.previred.desafio.domain.models.Usuario;
import cl.previred.desafio.domain.ports.in.usecases.estado_tarea.IFindEstadoTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.tarea.find.IFindTareaByIdUseCase;
import cl.previred.desafio.domain.ports.in.usecases.usuario.IFindUsuarioByIdUseCase;
import cl.previred.desafio.domain.ports.out.repositories.ITareaRepository;
import cl.previred.desafio.mocks.EstadoTareaMock;
import cl.previred.desafio.mocks.TareaMock;
import cl.previred.desafio.mocks.UsuarioMock;
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
class UpdateTareaUseCaseTest {
    @Mock
    private IFindTareaByIdUseCase findTareaByIdUseCase;
    @Mock
    private IFindEstadoTareaByIdUseCase findEstadoTareaByIdUseCase;
    @Mock
    private  IFindUsuarioByIdUseCase findUsuarioByIdUseCase;
    @Mock
    private ITareaRepository repository;
    @InjectMocks
    UpdateTareaUseCase updateTareaUseCase;

    @Test
    void name() {
        TareaMock.clearTarea1();
    }

    @Test
    @DisplayName("Actualización de tarea [exitoso]")
    void execute() {
         Tarea tarea1ToUpdate=Tarea.builder()
                 .id(1L)
                 .nombre("Tarea 1 actualizada")
                 .description("Descripcion actualizada")
                 .estado(EstadoTarea.builder().id(2L).build())
                 .usuarioAsignado(Usuario.builder().id(2L).build())
                 .usuarioCreador(UsuarioMock.usuario1)
                 .build();

        when(findTareaByIdUseCase.execute(1L)).thenReturn(TareaMock.tarea1);
        when(findEstadoTareaByIdUseCase.execute(2L)).thenReturn(EstadoTareaMock.estadoTarea2);
        when(findUsuarioByIdUseCase.execute(2L)).thenReturn(UsuarioMock.usuario2);
        when(repository.save(TareaMock.tarea1)).thenReturn(TareaMock.tarea1);
        updateTareaUseCase.execute(tarea1ToUpdate);
        assertAll(
                ()-> assertNotNull(tarea1ToUpdate,"La tarea no fue actualiza"),
                () ->assertEquals(2L, TareaMock.tarea1.getEstado().getId(),"estado tarea no fue actualizado"),
                () ->assertNotNull(TareaMock.tarea1.getUsuarioAsignado(),"usuario asignado no fue actualizado"),
                () ->assertNotNull(TareaMock.tarea1.getFechaAsignacion(),"fecha de asignacion no fue actualizado")
        );
    }
    @Test
    @DisplayName("Actualización de tarea sin estado y sin usuario asignado (null)")
    void executeWithStateAndUserAsignNull() {
        Tarea tareaUpdateRequest=Tarea.builder()
                .id(1L)
                .nombre("Tarea")
                .description("Descr")
                .fechaCreacion(LocalDateTime.parse("2023-10-01T10:00:00"))
                .usuarioCreador(UsuarioMock.usuario1)
                .build();
        when(findTareaByIdUseCase.execute(1L)).thenReturn(TareaMock.tarea1);
        when(repository.save(TareaMock.tarea1)).thenReturn(TareaMock.tarea1);
        //Tarea con datos a actualizar
        // No cuenta con usuario asignado ni estado, por ende, si el original ya cuenta con ellos no deberia actualizarlos en null

        var tareaUpdated=updateTareaUseCase.execute(tareaUpdateRequest);
        assertAll(
                ()-> assertNotNull(tareaUpdated,"tarea retornada es null"),
                ()-> assertNotNull(tareaUpdated.getEstado(),"estado tarea retornado es null"),
                ()-> assertNotNull(tareaUpdated.getUsuarioAsignado(),"usuario asignado retornado es null"),
                () ->assertNotNull(tareaUpdated.getFechaAsignacion(),"Fechas asignacion debe ser null")
        );
        verify(findEstadoTareaByIdUseCase,never()).execute(1L);
    }

    @Test
    @DisplayName("Actualización de tarea con estado y sin usuario asignado vacios (no null)")
    void executeWithStateAndUserAsignEmpty() {
        // Tarea que se va actualizar
        Tarea tareaOriginal=Tarea.builder()
                .id(1L)
                .nombre("Tarea 1")
                .description("Descripcion de la tarea 1")
                .fechaCreacion(LocalDateTime.parse("2023-10-01T10:00:00"))
                .estado(EstadoTareaMock.estadoTarea1)
                .usuarioCreador(UsuarioMock.usuario1)
                .usuarioAsignado(UsuarioMock.usuario2)
                .build();
        //Tarea con datos a actualizar
        // No cuenta con usuario asignado ni estado, por ende, si el original ya cuenta con ellos no deberia actualizarlos en null
        Tarea tarea=Tarea.builder()
                .id(1L)
                .nombre("Tarea")
                .description("Descr")
                .fechaCreacion(LocalDateTime.parse("2023-10-01T10:00:00"))
                .usuarioCreador(UsuarioMock.usuario1)
                .usuarioAsignado(new Usuario())
                .estado(new EstadoTarea())
                .build();


        tareaOriginal.setDescription(tarea.getDescription());
        tareaOriginal.setNombre(tarea.getNombre());

        when(findTareaByIdUseCase.execute(1L)).thenReturn(TareaMock.tarea1);
        when(repository.save(TareaMock.tarea1)).thenReturn(tareaOriginal);

        var tareaUpdated=updateTareaUseCase.execute(tarea);
        assertAll(
                ()-> assertNotNull(tareaUpdated,"tarea retornada es null"),
                ()-> assertNotNull(tareaUpdated.getEstado(),"estado tarea retornado es null"),
                ()-> assertNotNull(tareaUpdated.getUsuarioAsignado(),"usuario asignado retornado es null")
        );
        verify(findEstadoTareaByIdUseCase,never()).execute(1L);
    }



}
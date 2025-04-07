package cl.previred.desafio.mocks;

import cl.previred.desafio.domain.models.Tarea;

import java.time.LocalDateTime;
import java.util.List;

public class TareaMock {
    public static Tarea tarea1=Tarea.builder()
                            .id(1L)
                            .nombre("Tarea 1")
                            .description("Descripcion de la tarea 1")
                            .fechaCreacion(LocalDateTime.parse("2023-10-01T10:00:00"))
                            .estado(EstadoTareaMock.estadoTarea1)
                            .usuarioCreador(UsuarioMock.usuario1)
                            .build();
    public static Tarea tarea2=Tarea.builder()
                            .id(2L)
                            .nombre("Tarea 2")
                            .description("Descripcion de la tarea 2")
                            .fechaCreacion(LocalDateTime.parse("2023-10-02T10:00:00"))
                            .estado(EstadoTareaMock.estadoTarea1)
                            .usuarioCreador(UsuarioMock.usuario1)
                            .build();
    public static Tarea tarea3=Tarea.builder()
                            .id(3L)
                            .nombre("Tarea 3")
                            .description("Descripcion de la tarea 3")
                            .fechaCreacion(LocalDateTime.parse("2023-10-03T10:00:00"))
                            .estado(EstadoTareaMock.estadoTarea2)
                            .usuarioCreador(UsuarioMock.usuario1)
                            .build();

    public static Tarea tareaWithUserAssign=Tarea.builder()
            .id(5L)
            .nombre("Tarea 5")
            .description("Descripcion de la tarea 5")
            .fechaCreacion(LocalDateTime.parse("2023-10-03T10:00:00"))
            .estado(EstadoTareaMock.estadoTarea1)
            .usuarioCreador(UsuarioMock.usuario1)
            .usuarioAsignado(UsuarioMock.usuario1)
            .fechaAsignacion(LocalDateTime.now())
            .build();

    public  static List<Tarea> tareas= List.of(tarea1,tarea2,tarea3);
    public  static List<Tarea> tareasTipo2=List.of(tarea1,tarea2);


    public static void clearTarea1(){
        tarea1=Tarea.builder()
                .id(1L)
                .nombre("Tarea 1")
                .description("Descripcion de la tarea 1")
                .fechaCreacion(LocalDateTime.parse("2023-10-01T10:00:00"))
                .estado(EstadoTareaMock.estadoTarea1)
                .usuarioCreador(UsuarioMock.usuario1)
                .build();
    }
}

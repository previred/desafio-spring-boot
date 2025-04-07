package cl.previred.desafio.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Tarea {
    private Long id;
    private String nombre;
    private String description;
    private EstadoTarea estado;
    private Usuario usuarioCreador;
    private Usuario usuarioAsignado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAsignacion;

}

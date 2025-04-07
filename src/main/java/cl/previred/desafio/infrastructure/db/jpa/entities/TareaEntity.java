package cl.previred.desafio.infrastructure.db.jpa.entities;

import cl.previred.desafio.domain.models.Tarea;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String description;
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoTareaEntity estado;
    @ManyToOne
    @JoinColumn(name = "usuario_creador_id")
    private UsuarioEntity usuarioCreador;
    @ManyToOne
    @JoinColumn(name = "usuario_asignado_id")
    private UsuarioEntity usuarioAsignado;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    public static TareaEntity fromDomain(Tarea domain){
        return TareaEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .description(domain.getDescription())
                .estado(EstadoTareaEntity.fromDomain(domain.getEstado()))
                .usuarioCreador(UsuarioEntity.fromDomain(domain.getUsuarioCreador()))
                .usuarioAsignado(domain.getUsuarioAsignado()!=null?UsuarioEntity.fromDomain(domain.getUsuarioAsignado()):null)
                .fechaCreacion(domain.getFechaCreacion())
                .fechaAsignacion(domain.getFechaAsignacion())
                .build();
    }

    public Tarea toDomain(){
        return Tarea.builder()
                .id(this.id)
                .nombre(this.nombre)
                .description(this.description)
                .estado(this.estado.toDomain())
                .usuarioCreador(this.usuarioCreador.toDomain())
                .usuarioAsignado(this.usuarioAsignado!=null?this.usuarioAsignado.toDomain():null)
                .fechaCreacion(this.fechaCreacion)
                .fechaAsignacion(this.fechaAsignacion)
                .build();
    }
}

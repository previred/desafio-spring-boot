package cl.previred.desafio.infrastructure.db.jpa.entities;

import cl.previred.desafio.domain.models.EstadoTarea;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estados_tarea")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EstadoTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;

    public static EstadoTareaEntity fromDomain(EstadoTarea domain) {
        return EstadoTareaEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .build();
    }
    public EstadoTarea toDomain() {
        return EstadoTarea.builder()
                .id(this.id)
                .nombre(this.nombre)
                .build();
    }
}

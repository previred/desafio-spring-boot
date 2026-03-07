package cl.previred.desafio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estados_tarea")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstadoTareaEntity {
    @Id
    private Long id; // No autogenerado para que coincida con nuestro import.sql

    @Column(nullable = false, unique = true)
    private String nombre;
}

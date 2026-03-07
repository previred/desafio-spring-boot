package com.previred.desafiostareas.repository.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad para estado de tarea
 */
@Entity
@Table(name = "estados_tarea")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;
}

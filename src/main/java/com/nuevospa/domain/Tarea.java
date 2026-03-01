package com.nuevospa.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoTarea estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
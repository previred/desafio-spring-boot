package com.provired.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el estado de una tarea (Ejemplo: "Pendiente", "En Progreso", "Completada").
 */
@Entity
@Table(name = "task_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tasks") // Evita StackOverflowError al imprimir la entidad
@Builder
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Task> tasks = new ArrayList<>();
}
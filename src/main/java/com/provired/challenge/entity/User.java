package com.provired.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Representa un usuario con sus tareas asignadas.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Nombre de usuario único.

    @Column(nullable = false)
    private String password; // Contraseña encriptada.

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Task> tasks = List.of(); // Lista de tareas asignadas.
}

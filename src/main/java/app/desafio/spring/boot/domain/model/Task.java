package app.desafio.spring.boot.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // Título de la tarea
    private String description;  // Descripción de la tarea
    private LocalDateTime createdAt = LocalDateTime.now();  // Fecha de creación

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private TaskStatus status;  // Relación con el estado de la tarea

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;  // Relación con el usuario asignado a la tarea
}

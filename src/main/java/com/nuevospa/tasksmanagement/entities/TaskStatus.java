package com.nuevospa.tasksmanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Represents a Task Status entity in the database.
 * Mapped to the 'task_status' table.
 */
@Entity
@Table(name = "task_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // Enables automatic date auditing
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code; // e.g., PENDING, IN_PROGRESS, COMPLETED

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
}

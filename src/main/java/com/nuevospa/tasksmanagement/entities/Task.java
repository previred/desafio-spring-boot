package com.nuevospa.tasksmanagement.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Task entity in the database.
 * Mapped to the 'tasks' table.
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // Enables automatic date auditing
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code; // Unique code for the task

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User assigned to this task


    @Column(name = "due_date")
    private LocalDate dueDate;

    @CreatedDate
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @LastModifiedDate
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @PrePersist
    public void generateCode() {
        if (this.code == null) {
            // Simple code generation, consider a more robust unique ID generator for production
            this.code = "TASK-" + System.currentTimeMillis();
        }
    }
}

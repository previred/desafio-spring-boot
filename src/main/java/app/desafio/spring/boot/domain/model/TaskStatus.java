package app.desafio.spring.boot.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status", nullable = false, unique = true)
    private String status;

    public TaskStatus(String status) {
        this.status = status;
    }
}

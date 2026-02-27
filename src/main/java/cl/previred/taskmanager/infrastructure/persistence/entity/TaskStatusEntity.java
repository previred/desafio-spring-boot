package cl.previred.taskmanager.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_tarea")
public class TaskStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected TaskStatusEntity() {}

    public TaskStatusEntity(String name) {
        this.name = name;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

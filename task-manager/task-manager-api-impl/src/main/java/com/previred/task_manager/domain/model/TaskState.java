package com.previred.task_manager.domain.model;



import jakarta.persistence.*;
        import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "sch_tareas" ,name="estados_tarea")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskState {

    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String descripcion;
    private LocalDateTime creado;
    private Date modificado;
    @OneToMany(mappedBy = "taskState",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

}



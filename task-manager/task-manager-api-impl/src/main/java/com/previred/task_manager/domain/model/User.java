package com.previred.task_manager.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "sch_tareas" ,name="usuarios")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();


}

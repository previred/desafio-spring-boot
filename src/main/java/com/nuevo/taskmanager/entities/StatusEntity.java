package com.nuevo.taskmanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_tarea")
@Data
public class StatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}

package com.previred.spa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TASK_STATUS")
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public TaskStatus() {}

    public TaskStatus(String name) {
        this.name = name;
    }
}

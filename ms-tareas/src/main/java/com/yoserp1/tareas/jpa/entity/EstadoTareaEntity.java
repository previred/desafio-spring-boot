package com.yoserp1.tareas.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoTareaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String nombre;
}

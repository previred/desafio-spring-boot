package com.yoserp1.tareas.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String titulo;

  @Column(length = 255)
  private String descripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estado_id", nullable = false)
  private EstadoTareaEntity estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private UsuarioEntity usuario;

  @CreationTimestamp
  @Column(name = "fecha_creacion", updatable = false)
  private LocalDateTime fechaCreacion;
}

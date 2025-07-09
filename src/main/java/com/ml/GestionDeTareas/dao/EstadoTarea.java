package com.ml.GestionDeTareas.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ESTADO_TAREA")
public class EstadoTarea {
    @Id
    private int id;
    private String descripcion;
}

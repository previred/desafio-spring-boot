package com.gestion.tarea.dto;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "tareas")
public class TareaDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tarea_generator")
    @SequenceGenerator
            (name="tarea_generator", sequenceName = "tarea_seq",
                    initialValue = 1,
                    allocationSize=500)
    private long id;

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private int estado;
    @Column(name = "fecha_cracion")
    private Date fechaCreacion;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

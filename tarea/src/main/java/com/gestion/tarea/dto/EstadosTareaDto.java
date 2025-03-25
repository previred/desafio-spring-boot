package com.gestion.tarea.dto;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "estados_tarea")

public class EstadosTareaDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "estado_tarea_generator")
    @SequenceGenerator
            (name="estado_tarea_generator", sequenceName = "estado_tarea_seq",
                    initialValue = 5,
                    allocationSize=500)
    private long id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "habilitado")
    private int habilitado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

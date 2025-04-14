package com.nuevospa.taskmanager.enums;

import lombok.Getter;

public enum EstadoTareaEnum {
    PE(1L, "Pendiente"),
    EP(2L, "En Progreso"),
    CO(3L, "Completada"),
    EL(4L, "Eliminada");

    private final Long id;
    private final String descripcion;

    EstadoTareaEnum(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static EstadoTareaEnum fromCodigo(String codigo) {
        for (EstadoTareaEnum estado : values()) {
            if (estado.name().equalsIgnoreCase(codigo)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + codigo);
    }
}
package com.example.usuarioytareas.dto;


public class TareaResponse {

    private Long id;
    private String titulo;
    private String descripcion;
    private String usuario;
    private String estado;

    public TareaResponse(Long id, String titulo, String descripcion, String usuario, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado;
    }
}

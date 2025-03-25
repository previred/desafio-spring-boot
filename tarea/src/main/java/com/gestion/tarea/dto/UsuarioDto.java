package com.gestion.tarea.dto;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class UsuarioDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_generator")
    @SequenceGenerator
            (name="user_generator", sequenceName = "user_seq",
                    initialValue = 5,
                    allocationSize=500)
    private long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "pass", length =60)
    private String pass;

    @Column(name = "rol")
    private String rol;

    @Column(name = "habilitado")
    private boolean habilitado;

    public UsuarioDto() {
    }

    public boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

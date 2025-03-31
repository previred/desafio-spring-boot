package gestion.tareas.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "estados_tarea")
@Data // Lombok
public class EstadoTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

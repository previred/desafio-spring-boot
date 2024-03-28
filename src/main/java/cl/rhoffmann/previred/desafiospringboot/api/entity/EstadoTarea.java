package cl.rhoffmann.previred.desafiospringboot.api.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa el estado de una tarea en la base de datos.
 * <p>
 * Esta entidad almacena los distintos estados que puede tener una tarea,
 * como por ejemplo "Pendiente", "En progreso", "Completada", etc.
 * Cada estado de tarea puede estar asociado a múltiples tareas.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Entity
@Table(name = "estados_tarea")
public class EstadoTarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nombre;

	@OneToMany(mappedBy = "estadoTarea")
	@JsonIgnoreProperties(value = { "estadoTarea" }, allowSetters = true)
	private Set<Tarea> tareas = new HashSet<>();

	/**
     * Constructor por defecto.
     */
	public EstadoTarea() {
		super();
	}
	
	/**
     * Constructor con el nombre del estado.
     *
     * @param nombre El nombre del estado de la tarea.
     */
	public EstadoTarea(String nombre) {
		super();
		this.nombre = nombre;
	}

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

	public Set<Tarea> getTareas() {
		return tareas;
	}

	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EstadoTarea [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append("]");
		return builder.toString();
	}

}

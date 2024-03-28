package cl.rhoffmann.previred.desafiospringboot.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa una tarea en la base de datos.
 * <p>
 * Esta entidad almacena información sobre tareas individuales, incluyendo su nombre, descripción,
 * el estado actual de la tarea (como pendiente, en progreso, completada), y el usuario asociado a la tarea.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Entity
@Table(name = "tareas")
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column
	private String descripcion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = { "tareas" }, allowSetters = true)
	private EstadoTarea estadoTarea;

	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	/**
     * Constructor por defecto.
     */
	public Tarea() {
		super();
	}
	
	 /**
     * Constructor que inicializa una nueva tarea con detalles específicos.
     *
     * @param id           El identificador único de la tarea.
     * @param nombre       El nombre de la tarea.
     * @param descripcion  La descripción de la tarea.
     * @param estadoTarea  El estado actual de la tarea.
     * @param usuario      El usuario asociado a la tarea.
     */
	public Tarea(Long id, String nombre, String descripcion, EstadoTarea estadoTarea, Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estadoTarea = estadoTarea;
		this.usuario = usuario;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoTarea getEstadoTarea() {
		return estadoTarea;
	}

	public void setEstadoTarea(EstadoTarea estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tarea [id=");
		builder.append(id);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", estadoTarea=");
		builder.append(estadoTarea);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}

}

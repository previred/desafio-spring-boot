package cl.rhoffmann.previred.desafiospringboot.api.entity;

import java.util.Collection;
import java.util.Collections;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidad que representa un usuario en la base de datos.
 * <p>
 * Esta entidad almacena información básica de los usuarios, como su correo, contraseña y nombre.
 * Implementa la interfaz {@link UserDetails} de Spring Security para integrarse con el sistema de autenticación y autorización.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 335266049953928827L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String correo;

	@Column(nullable = false)
	private String contrasena;

	@Column(nullable = false)
	private String nombre;

	/**
     * Constructor por defecto.
     */
	public Usuario() {
		super();
	}

	/**
     * Constructor que inicializa un usuario con su correo, contraseña y nombre.
     *
     * @param correo     El correo electrónico del usuario, que también actúa como su identificador único.
     * @param contrasena La contraseña del usuario.
     * @param nombre     El nombre del usuario.
     */
	public Usuario(String correo, String contrasena, String nombre) {
		super();
		this.correo = correo;
		this.contrasena = contrasena;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", correo=");
		builder.append(correo);
		builder.append(", contrasena=");
		builder.append(contrasena);
		builder.append(", nombre=");
		builder.append(nombre);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return getContrasena();
	}

	@Override
	public String getUsername() {
		return getCorreo();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.UsuarioDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;

/**
 * Mapper para convertir entre la entidad Usuario y el UsuarioDTO.
 * <p>
 * Proporciona métodos para transformar una entidad Usuario a su correspondiente DTO y viceversa. 
 * Esto es especialmente útil para aislar la capa de dominio de la capa de presentación y transferencia de datos,
 * facilitando la abstracción y el encapsulamiento de los datos.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class UsuarioMapper {

	/**
     * Convierte una entidad Usuario a un UsuarioDTO.
     *
     * @param usuario La entidad Usuario a convertir.
     * @return Un objeto UsuarioDTO que representa los datos del usuario, o null si el usuario es null.
     */
	public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) {
		if(usuario == null) {
			return null;
		}
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setCorreo(usuario.getCorreo());
		usuarioDTO.setNombre(usuario.getNombre());
		usuarioDTO.setId(usuario.getId());
		return usuarioDTO;
	}

	/**
     * Convierte un UsuarioDTO a una entidad Usuario.
     * <p>
     * Este método crea una nueva entidad Usuario y la actualiza con los datos proporcionados por el DTO.
     * Es útil para crear o actualizar entidades basadas en los datos recibidos desde capas superiores,
     * como la capa de presentación o la API.
     * </p>
     *
     * @param usuarioDTO El DTO de Usuario que contiene los datos.
     * @return Una entidad Usuario nueva actualizada con los datos del DTO, o null si el DTO es null.
     */
	public Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO) {
		if(usuarioDTO == null) {
			return null;
		}
		Usuario usuario = new Usuario();
		actualizarUsuarioDesdeDTO(usuarioDTO, usuario);
		return usuario;
	}

	/**
	 * Actualiza los campos de una entidad Usuario existente con los valores proporcionados por un UsuarioDTO.
	 * <p>
	 * Este método realiza la actualización de una entidad Usuario, aplicando cambios de manera condicional.
	 * Utiliza {@link Optional} para verificar la presencia de cada campo en el DTO antes de realizar la actualización.
	 * Esto evita sobrescribir valores existentes con null en caso de que el DTO no especifique un nuevo valor para
	 * alguno de los campos, permitiendo así actualizaciones parciales de la entidad.
	 * </p>
	 *
	 * @param usuarioDTO El DTO de Usuario que contiene los datos potenciales para la actualización.
	 * @param usuario    La entidad Usuario existente a actualizar. Si el usuario es null, el método termina prematuramente.
	 */
	public void actualizarUsuarioDesdeDTO(UsuarioDTO usuarioDTO, Usuario usuario) {
		if(usuario == null) {
			return;
		}
		Optional.ofNullable(usuarioDTO.getCorreo()).ifPresent(usuario::setCorreo);
	    Optional.ofNullable(usuarioDTO.getNombre()).ifPresent(usuario::setNombre);
	    Optional.ofNullable(usuarioDTO.getId()).ifPresent(usuario::setId);
	}
}

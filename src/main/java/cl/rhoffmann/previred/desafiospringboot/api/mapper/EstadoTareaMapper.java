package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.EstadoTareaDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;


/**
 * Mapper para convertir entre la entidad EstadoTarea y su correspondiente Data Transfer Object (DTO).
 * <p>
 * Proporciona métodos para convertir una instancia de {@link EstadoTarea} a {@link EstadoTareaDTO} y viceversa,
 * facilitando la separación entre la capa de persistencia y la capa de presentación/modelo de dominio de la aplicación.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class EstadoTareaMapper {

	/**
     * Convierte una entidad EstadoTarea a un EstadoTareaDTO.
     * <p>
     * Este método transforma una entidad EstadoTarea, utilizada en la capa de persistencia, a un DTO
     * que se puede utilizar en las capas de servicio y presentación, aislando así las diferentes capas
     * de la aplicación y facilitando el intercambio de datos.
     * </p>
     *
     * @param estadoTarea la entidad EstadoTarea que se va a convertir.
     * @return un objeto {@link EstadoTareaDTO} que representa el DTO de la entidad EstadoTarea.
     */
	public EstadoTareaDTO estadoTareaToEstadoTareaDTO(EstadoTarea estadoTarea) {
		if(estadoTarea == null) {
			return null;
		}
		EstadoTareaDTO estadoTareaDTO = new EstadoTareaDTO();
		estadoTareaDTO.setId(estadoTarea.getId());
		estadoTareaDTO.setNombre(estadoTarea.getNombre());
		
		return estadoTareaDTO;
	}

	/**
     * Convierte un EstadoTareaDTO a una entidad EstadoTarea.
     * <p>
     * Realiza la operación inversa al método anterior, transformando un DTO a su correspondiente
     * entidad. Esto es útil para cuando se reciben datos de la capa de presentación que deben ser
     * persistidos en la base de datos.
     * </p>
     *
     * @param estadoTareaDTO el DTO de EstadoTarea que se va a convertir.
     * @return una entidad {@link EstadoTarea} que representa la entidad de persistencia correspondiente al DTO.
     */
	public EstadoTarea estadoTareaDTOToEstadoTarea(EstadoTareaDTO estadoTareaDTO) {
		if(estadoTareaDTO == null) {
			return null;
		}
		EstadoTarea estadoTarea = new EstadoTarea();
		estadoTarea.setId(estadoTareaDTO.getId());
		estadoTarea.setNombre(estadoTarea.getNombre());
		return estadoTarea;
	}

}

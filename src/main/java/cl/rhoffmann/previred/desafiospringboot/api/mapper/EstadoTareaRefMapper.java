package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.EstadoTareaRefDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;

/**
 * Mapper para convertir entre el DTO de referencia EstadoTareaRefDTO y la entidad EstadoTarea.
 * <p>
 * Este mapper se enfoca en convertir un DTO que representa una referencia (incluyendo solo el identificador)
 * de EstadoTarea a la entidad EstadoTarea completa. Es útil para operaciones que requieren la creación o actualización
 * de entidades basadas en una referencia de otra entidad, simplificando la manipulación y persistencia de relaciones
 * entre entidades.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class EstadoTareaRefMapper {

	/**
     * Convierte un EstadoTareaRefDTO a una entidad EstadoTarea.
     * <p>
     * Este método toma un DTO que contiene solo el identificador de una entidad EstadoTarea
     * y construye una entidad EstadoTarea solo con su identificador seteado. Esto es particularmente útil
     * para operaciones de persistencia donde solo es necesario referenciar la entidad por su ID sin necesidad
     * de cargar toda la entidad desde la base de datos.
     * </p>
     *
     * @param estadoTareaRefDTO el DTO de referencia de EstadoTarea que se va a convertir.
     * @return una entidad {@link EstadoTarea} con solo su identificador seteado, listo para ser usado en referencias.
     */
	public EstadoTarea estadoTareaRefDTOToEstadoTarea(EstadoTareaRefDTO estadoTareaRefDTO) {
		if(estadoTareaRefDTO == null) {
			return null;
		}
		EstadoTarea estadoTarea = new EstadoTarea();
		estadoTarea.setId(estadoTareaRefDTO.getId());
		return estadoTarea;
	}

}

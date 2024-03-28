package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPutDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Tarea;

/**
 * Mapper para actualizar una entidad Tarea existente con datos provenientes de un TareaPutDTO.
 * <p>
 * Este mapper se especializa en aplicar cambios a una tarea existente, utilizando datos de un DTO diseñado
 * para operaciones de actualización (PUT). A diferencia de la creación, la actualización puede omitir
 * campos, permitiendo cambios parciales en la entidad.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class TareaPutMapper {

	private final EstadoTareaRefMapper estadoTareaRefMapper;

	/**
     * Constructor que inyecta las dependencias necesarias para el mapeo.
     *
     * @param estadoTareaRefMapper Mapper para convertir referencias de EstadoTarea desde su DTO.
     */
	public TareaPutMapper(EstadoTareaRefMapper estadoTareaRefMapper) {
		this.estadoTareaRefMapper = estadoTareaRefMapper;
	}

	/**
     * Actualiza los campos de una entidad Tarea existente con los valores proporcionados por un TareaPutDTO.
     * <p>
     * Solo los campos presentes en el TareaPutDTO serán actualizados en la entidad Tarea, permitiendo
     * actualizaciones parciales. Utiliza {@link Optional} para determinar si un valor está presente en el DTO
     * antes de aplicarlo a la entidad, evitando así sobrescribir valores con null.
     * </p>
     *
     * @param tareaPutDTO El DTO que contiene los datos a actualizar.
     * @param tarea       La entidad Tarea existente que se actualizará.
     */
	public void actualizarTareaDesdeDTO(TareaPutDTO tareaPutDTO, Tarea tarea) {
		if(tarea == null) {
			return;
		}
		Optional.ofNullable(tareaPutDTO.getDescripcion()).ifPresent(tarea::setDescripcion);
	    Optional.ofNullable(tareaPutDTO.getEstadoTarea()).ifPresent(estadoDTO -> tarea.setEstadoTarea(estadoTareaRefMapper.estadoTareaRefDTOToEstadoTarea(estadoDTO)));
	    Optional.ofNullable(tareaPutDTO.getNombre()).ifPresent(tarea::setNombre);

	}

}

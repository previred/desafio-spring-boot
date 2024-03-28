package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Tarea;

/**
 * Mapper para convertir entre la entidad Tarea y su correspondiente Data Transfer Object (DTO).
 * <p>
 * Este mapper utiliza otros mappers específicos para las entidades relacionadas, como EstadoTarea y Usuario,
 * para proporcionar una conversión completa entre Tarea y TareaDTO, asegurando que todas las propiedades,
 * incluidas las relaciones con otras entidades, sean correctamente transformadas.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class TareaMapper {

	private final EstadoTareaMapper estadoTareaMapper;
	private final UsuarioMapper usuarioMapper;
	
	/**
     * Constructor que inyecta las dependencias requeridas para el mapeo.
     *
     * @param estadoTareaMapper Mapper para EstadoTarea.
     * @param usuarioMapper     Mapper para Usuario.
     */
	public TareaMapper(EstadoTareaMapper estadoTareaMapper, UsuarioMapper usuarioMapper) {
		this.estadoTareaMapper = estadoTareaMapper;
		this.usuarioMapper = usuarioMapper;
	}

	/**
     * Convierte una entidad Tarea a un TareaDTO.
     *
     * @param tarea La entidad Tarea que se va a convertir.
     * @return Un objeto TareaDTO que representa la tarea.
     */
	public TareaDTO tareaToTareaDTO(Tarea tarea) {
		if(tarea == null) {
			return null;
		}
		TareaDTO tareaDTO = new TareaDTO();
		tareaDTO.setDescripcion(tarea.getDescripcion());
		tareaDTO.setEstadoTarea(estadoTareaMapper.estadoTareaToEstadoTareaDTO(tarea.getEstadoTarea()));
		tareaDTO.setId(tarea.getId());
		tareaDTO.setNombre(tarea.getNombre());
		tareaDTO.setUsuario(usuarioMapper.usuarioToUsuarioDTO(tarea.getUsuario()));
		return tareaDTO;
	}

	/**
     * Convierte un TareaDTO a una entidad Tarea.
     * <p>
     * Este método crea una nueva entidad Tarea y la actualiza con los datos del DTO.
     * </p>
     *
     * @param tareaDTO El DTO de Tarea que se va a convertir.
     * @return Una nueva entidad Tarea basada en el DTO.
     */
	public Tarea tareaDTOToTarea(TareaDTO tareaDTO) {
		if(tareaDTO == null) {
			return null;
		}
		Tarea tarea = new Tarea();
		actualizarTareaDesdeDTO(tareaDTO, tarea);
		return tarea;
	}

	/**
     * Actualiza los datos de una entidad Tarea existente con los valores de un TareaDTO.
     * <p>
     * Este método es útil para actualizar una entidad Tarea existente con datos nuevos provenientes de un DTO,
     * como parte de operaciones de actualización.
     * </p>
     *
     * @param tareaDTO El DTO de Tarea con los nuevos datos.
     * @param tarea    La entidad Tarea existente que se actualizará.
     */
	public void actualizarTareaDesdeDTO(TareaDTO tareaDTO, Tarea tarea) {
		if(tarea == null) {
			return;
		}
		tarea.setDescripcion(tareaDTO.getDescripcion());
		tarea.setEstadoTarea(estadoTareaMapper.estadoTareaDTOToEstadoTarea(tareaDTO.getEstadoTarea()));
		tarea.setId(tareaDTO.getId());
		tarea.setNombre(tareaDTO.getNombre());
		tarea.setUsuario(usuarioMapper.usuarioDTOToUsuario(tareaDTO.getUsuario()));
	}
}

package cl.rhoffmann.previred.desafiospringboot.api.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPostDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Tarea;

/**
 * Mapper para convertir un TareaPostDTO a una entidad Tarea, con un estado predeterminado.
 * <p>
 * Este mapper se especializa en el procesamiento de datos de creación de tareas, configurando un estado inicial
 * para la tarea basado en propiedades definidas en la configuración de la aplicación. Es útil para asegurar que
 * todas las tareas nuevas se creen con un estado inicial consistente.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class TareaPostMapper {

	@Value("${estadotarea1.nombre}")
	private String estadoTarea1Nombre;
	
	@Value("${estadotarea1.id}")
	private Long estadoTarea1Id;
	
	/**
     * Convierte un TareaPostDTO a una entidad Tarea, asignando un estado inicial a la tarea.
     * <p>
     * Utiliza una propiedad configurada para determinar el nombre del estado inicial de la tarea,
     * creando un objeto EstadoTarea que se asigna a la nueva tarea. Esta aproximación garantiza que
     * la tarea esté correctamente inicializada con el estado deseado en el momento de su creación.
     * </p>
     *
     * @param tareaPostDTO El DTO de creación de tarea que contiene los datos necesarios.
     * @return Una nueva entidad Tarea con un estado inicial preconfigurado y los datos proporcionados por el DTO.
     */
	public Tarea tareaPostDTOToTarea(TareaPostDTO tareaPostDTO) {
		if (tareaPostDTO == null) {
			return null;
		}
		EstadoTarea estadoTarea = new EstadoTarea();
		estadoTarea.setId(estadoTarea1Id);
		estadoTarea.setNombre(estadoTarea1Nombre);
		Tarea tarea = new Tarea();
		tarea.setDescripcion(tareaPostDTO.getDescripcion());
		tarea.setEstadoTarea(estadoTarea);
		tarea.setNombre(tareaPostDTO.getNombre());
		return tarea;
	}
}

package cl.rhoffmann.previred.desafiospringboot.api.serviceimpl;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPostDTO;
import cl.rhoffmann.previred.desafiospringboot.api.dto.TareaPutDTO;
import cl.rhoffmann.previred.desafiospringboot.api.entity.EstadoTarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Tarea;
import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import cl.rhoffmann.previred.desafiospringboot.api.exception.EstadoTareaNoEncontradoException;
import cl.rhoffmann.previred.desafiospringboot.api.exception.TareaNoEncontradaException;
import cl.rhoffmann.previred.desafiospringboot.api.exception.UsuarioNoEncontradoException;
import cl.rhoffmann.previred.desafiospringboot.api.mapper.TareaMapper;
import cl.rhoffmann.previred.desafiospringboot.api.mapper.TareaPostMapper;
import cl.rhoffmann.previred.desafiospringboot.api.mapper.TareaPutMapper;
import cl.rhoffmann.previred.desafiospringboot.api.repository.EstadoTareaRepository;
import cl.rhoffmann.previred.desafiospringboot.api.repository.TareaRepository;
import cl.rhoffmann.previred.desafiospringboot.api.repository.UsuarioRepository;
import cl.rhoffmann.previred.desafiospringboot.api.service.TareaService;

import java.util.List;

/**
 * Implementación del servicio para gestionar tareas, incluyendo operaciones CRUD.
 * <p>
 * Esta clase integra los repositorios para usuarios, tareas y estados de tareas, junto con mappers
 * para convertir entre entidades y DTOs. Asegura que las operaciones de gestión de tareas se realicen
 * en el contexto del usuario autenticado actual, y aplica reglas de negocio como restricciones de estado.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Service
public class TareaServiceImpl implements TareaService {

	private final UsuarioRepository usuarioRepository;
	private final TareaRepository tareaRepository;
	private final EstadoTareaRepository estadoTareaRepository;
	private final TareaMapper tareaMapper;
	private final TareaPostMapper tareaPostMapper;
	private final TareaPutMapper tareaPutMapper;

	/**
     * Constructor para inyectar las dependencias necesarias para el servicio de gestión de tareas.
     * <p>
     * Este constructor recibe los repositorios y mappers necesarios para operar con las entidades de tarea,
     * estado de tarea y usuario, facilitando la interacción entre el modelo de datos y la capa de servicio.
     * </p>
     *
     * @param usuarioRepository Repositorio para la entidad Usuario, utilizado para recuperar el usuario autenticado.
     * @param tareaRepository Repositorio para la entidad Tarea, proporciona acceso a las operaciones CRUD de tareas.
     * @param estadoTareaRepository Repositorio para la entidad EstadoTarea, permite acceder a los estados de las tareas.
     * @param tareaMapper Mapper para convertir entre la entidad Tarea y su DTO.
     * @param tareaPostMapper Mapper para transformar un TareaPostDTO en una entidad Tarea.
     * @param tareaPutMapper Mapper para actualizar una entidad Tarea existente a partir de un TareaPutDTO.
     */
	public TareaServiceImpl(UsuarioRepository usuarioRepository, TareaRepository tareaRepository,
			EstadoTareaRepository estadoTareaRepository, TareaMapper tareaMapper, TareaPostMapper tareaPostMapper,
			TareaPutMapper tareaPutMapper) {
		this.usuarioRepository = usuarioRepository;
		this.tareaRepository = tareaRepository;
		this.estadoTareaRepository = estadoTareaRepository;
		this.tareaMapper = tareaMapper;
		this.tareaPostMapper = tareaPostMapper;
		this.tareaPutMapper = tareaPutMapper;
	}

	/**
     * Obtiene una lista paginada de tareas asociadas al usuario autenticado.
     *
     * @param pageable Configuración de paginación y ordenación.
     * @return Lista de {@link TareaDTO} para el usuario autenticado.
     */
	@Transactional(readOnly = true)
	public List<TareaDTO> obtenerTodos(Pageable pageable) {
		return tareaRepository.findByUsuario(obtenerUsuarioAutenticado(), pageable).getContent().stream()
				.map(tareaMapper::tareaToTareaDTO).toList();
	}

	/**
     * Obtiene los detalles de una tarea específica por ID, asegurando que pertenezca al usuario autenticado.
     *
     * @param id Identificador de la tarea a buscar.
     * @return {@link TareaDTO} con los detalles de la tarea.
     * @throws TareaNoEncontradaException si la tarea no existe o no pertenece al usuario autenticado.
     */
	@Transactional(readOnly = true)
	public TareaDTO obtenerPorId(Long id) {
		return tareaRepository.findByIdAndUsuario(id, obtenerUsuarioAutenticado()).map(tareaMapper::tareaToTareaDTO)
				.orElseThrow(TareaNoEncontradaException::new);
	}

	/**
     * Crea una nueva tarea con los datos proporcionados, asociándola al usuario autenticado.
     *
     * @param tareaPostDTO Datos para la creación de la nueva tarea.
     * @return {@link TareaDTO} con los detalles de la tarea creada.
     */
	@Transactional
	public TareaDTO crear(TareaPostDTO tareaPostDTO) {
		Tarea tarea = tareaPostMapper.tareaPostDTOToTarea(tareaPostDTO);
		tarea.setUsuario(obtenerUsuarioAutenticado());
		tarea = tareaRepository.save(tarea);
		return tareaMapper.tareaToTareaDTO(tarea);
	}

	/**
     * Actualiza una tarea existente con los datos proporcionados, validando que pertenezca al usuario autenticado.
     *
     * @param id          Identificador de la tarea a actualizar.
     * @param tareaPutDTO Datos para actualizar la tarea.
     * @return {@link TareaDTO} con los detalles actualizados de la tarea.
     * @throws TareaNoEncontradaException si la tarea no existe o no pertenece al usuario autenticado.
     */
	@Transactional
	public TareaDTO actualizar(Long id, TareaPutDTO tareaPutDTO) {
		Tarea tarea = tareaRepository.findByIdAndUsuario(id, obtenerUsuarioAutenticado())
				.orElseThrow(TareaNoEncontradaException::new);

		EstadoTarea estadoTarea = tarea.getEstadoTarea();
		if (tareaPutDTO.getEstadoTarea() != null) {
			estadoTarea = estadoTareaRepository.findById(tareaPutDTO.getEstadoTarea().getId())
					.orElseThrow(EstadoTareaNoEncontradoException::new);
		}

		tareaPutMapper.actualizarTareaDesdeDTO(tareaPutDTO, tarea);
		tarea.setEstadoTarea(estadoTarea);
		tarea = tareaRepository.save(tarea);

		return tareaMapper.tareaToTareaDTO(tarea);
	}

	/**
     * Elimina una tarea específica, verificando que pertenezca al usuario autenticado.
     *
     * @param id Identificador de la tarea a eliminar.
     * @throws TareaNoEncontradaException si la tarea no existe o no pertenece al usuario autenticado.
     */
	@Transactional
	public void eliminar(Long id) {
		Tarea tarea = tareaRepository.findByIdAndUsuario(id, obtenerUsuarioAutenticado())
				.orElseThrow(TareaNoEncontradaException::new);
		tareaRepository.delete(tarea);
	}

	/**
     * Recupera el usuario autenticado actual del contexto de seguridad y lo busca en la base de datos.
     * <p>
     * Este método es crucial para asegurar que las operaciones de gestión de tareas se realicen
     * en el contexto del usuario autenticado, aplicando una capa simple adicional de personalización.
     * </p>
     *
     * @return El {@link Usuario} autenticado actualmente.
     * @throws UsuarioNoEncontradoException si el usuario no se encuentra en la base de datos.
     */
	private Usuario obtenerUsuarioAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String correo = authentication.getName();

		return usuarioRepository.findByCorreo(correo).orElseThrow(UsuarioNoEncontradoException::new);
	}
}

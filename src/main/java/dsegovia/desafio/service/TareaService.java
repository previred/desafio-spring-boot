package dsegovia.desafio.service;

import dsegovia.desafio.entity.Tarea;
import dsegovia.desafio.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para operaciones con tareas
 */
public interface TareaService {

    /**
     * Crea una nueva tarea para el usuario actual
     *
     * @param tarea La tarea a crear
     * @return La tarea creada con su ID asignado
     */
    Tarea crearTarea(Tarea tarea);

    /**
     * Actualiza una tarea existente
     *
     * @param id ID de la tarea a actualizar
     * @param tarea Datos actualizados de la tarea
     * @return La tarea actualizada
     * @throws Exception Si la tarea no existe o el usuario no tiene permisos
     */
    Tarea actualizarTarea(Long id, Tarea tarea) throws Exception;

    /**
     * Elimina una tarea
     *
     * @param id ID de la tarea a eliminar
     * @throws Exception Si la tarea no existe o el usuario no tiene permisos
     */
    void eliminarTarea(Long id) throws Exception;

    /**
     * Obtiene una tarea por su ID
     *
     * @param id ID de la tarea
     * @return Optional con la tarea si existe, vacío si no
     */
    Optional<Tarea> obtenerTareaById(Long id);

    /**
     * Verifica si el usuario actual tiene permiso para modificar la tarea
     *
     * @param tarea La tarea a verificar
     * @param usuario El usuario que intenta modificar
     * @return true si el usuario es el propietario o tiene rol ADMIN
     */
    boolean tienePermiso(Tarea tarea, Usuario usuario);

    /**
     * Obtiene las tareas del usuario actual, opcionalmente filtradas
     *
     * @param estadoId ID del estado para filtrar (opcional)
     * @param prioridad Prioridad para filtrar (opcional)
     * @param etiqueta Etiqueta para filtrar (opcional)
     * @return Lista de tareas filtradas
     */
    List<Tarea> obtenerTareasFiltradas(Long estadoId, Tarea.Prioridad prioridad, String etiqueta);

    /**
     * Obtiene el usuario autenticado actualmente
     *
     * @return Usuario autenticado
     */
    Usuario obtenerUsuarioActual();
}
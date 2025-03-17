package com.cifuentes.cristobal.gestion.service;

import java.util.List;

import com.cifuentes.cristobal.gestion.model.dto.TaskCreateRequest;
import com.cifuentes.cristobal.gestion.model.dto.TaskResponse;
import com.cifuentes.cristobal.gestion.model.dto.TaskUpdateRequest;

/**
 * Servicio que define las operaciones de negocio relacionadas con la gestión de tareas.
 *
 * Esta interfaz abstrae la lógica de creación, consulta, modificación y eliminación lógica
 * de tareas asociadas a un usuario específico.
 */
public interface TaskService {

    /**
     * Crea una nueva tarea para el usuario especificado.
     *
     * @param request Objeto con los datos de la tarea a crear.
     * @param userId ID del usuario al que se asociará la tarea.
     * @return La tarea creada, encapsulada en un DTO de respuesta.
     */
    TaskResponse createTask(TaskCreateRequest request, Long userId);

    /**
     * Obtiene todas las tareas del usuario que no han sido eliminadas.
     *
     * @param userId ID del usuario.
     * @return Lista de tareas asociadas al usuario.
     */
    List<TaskResponse> getTasks(Long userId);

    /**
     * Obtiene una tarea específica del usuario por su ID.
     *
     * @param taskId ID de la tarea a buscar.
     * @param userId ID del usuario propietario.
     * @return La tarea encontrada, encapsulada en un DTO.
     * @throws RuntimeException si la tarea no existe o fue eliminada.
     */
    TaskResponse getTaskById(Long taskId, Long userId);

    /**
     * Elimina lógicamente una tarea del usuario.
     *
     * @param taskId ID de la tarea a eliminar.
     * @param userId ID del usuario propietario.
     */
    void deleteTask(Long taskId, Long userId);

    /**
     * Actualiza los datos de una tarea existente (título y descripción).
     *
     * @param taskId ID de la tarea a actualizar.
     * @param request DTO con los nuevos datos.
     * @param userId ID del usuario propietario.
     * @return La tarea actualizada.
     */
    TaskResponse updateTask(Long taskId, TaskUpdateRequest request, Long userId);

    /**
     * Cambia el estado de una tarea (por ejemplo, de PENDING a COMPLETED).
     *
     * @param taskId ID de la tarea.
     * @param statusName Nuevo estado.
     * @param userId ID del usuario propietario.
     * @return La tarea con el nuevo estado aplicado.
     */
    TaskResponse updateTaskStatus(Long taskId, String statusName, Long userId);
}
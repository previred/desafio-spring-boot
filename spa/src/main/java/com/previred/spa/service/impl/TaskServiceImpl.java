package com.previred.spa.service.impl;

import com.previred.spa.dto.TaskDTO;
import com.previred.spa.dto.TaskRequestDTO;
import com.previred.spa.entity.Task;
import com.previred.spa.entity.TaskStatus;
import com.previred.spa.entity.User;
import com.previred.spa.exception.BadRequestException;
import com.previred.spa.exception.ResourceNotFoundException;
import com.previred.spa.repository.TaskRepository;
import com.previred.spa.repository.TaskStatusRepository;
import com.previred.spa.repository.UserRepository;
import com.previred.spa.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene las tareas asociadas a un usuario específico.
     *
     * @param username Nombre de usuario del propietario de las tareas.
     * @return Lista de tareas convertidas a DTO.
     */
    @Override
    public List<TaskDTO> getTasksForUser(String username) {
        User user = findUserByUsername(username);
        List<Task> tasks = taskRepository.findByUserId(user.getId());
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Crea una nueva tarea para un usuario autenticado.
     *
     * @param taskRequestDTO DTO que contiene los datos de la tarea a crear.
     * @param username Nombre de usuario del propietario de la nueva tarea.
     * @return DTO de la tarea creada.
     */
    @Override
    public TaskDTO createTaskForUser(TaskRequestDTO taskRequestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));

        TaskStatus status = taskStatusRepository.findByName(taskRequestDTO.getStatus())
                .orElseThrow(() -> new BadRequestException("Estado de la tarea no es válido: " + taskRequestDTO.getStatus()));
        Task task = modelMapper.map(taskRequestDTO, Task.class);
        task.setUser(user);
        task.setStatus(status);
        Task createdTask = taskRepository.save(task);
        return modelMapper.map(createdTask, TaskDTO.class);
    }



    /**
     * Actualiza una tarea existente para un usuario autenticado.
     *
     * @param taskId ID de la tarea que se desea actualizar.
     * @param taskRequestDTO DTO con los datos actualizados de la tarea.
     * @param username Nombre de usuario del propietario de la tarea.
     * @return DTO de la tarea actualizada.
     */
    @Override
    public TaskDTO updateTaskForUser(Long taskId, TaskRequestDTO taskRequestDTO, String username) {
        User user = findUserByUsername(username);
        Task existingTask = findTaskByIdAndUser(taskId, user);
        modelMapper.map(taskRequestDTO, existingTask);
        TaskStatus newStatus = findTaskStatusByName(taskRequestDTO.getStatus());
        existingTask.setStatus(newStatus);
        Task updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDTO.class);
    }


    /**
     * Elimina una tarea existente para un usuario autenticado.
     *
     * @param taskId ID de la tarea que se desea eliminar.
     * @param username Nombre de usuario del propietario de la tarea.
     */
    @Override
    public void deleteTaskForUser(Long taskId, String username) {
        User user = findUserByUsername(username);
        Task existingTask = findTaskByIdAndUser(taskId, user);
        taskRepository.delete(existingTask);
    }

    /**
     * Encuentra un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Entidad `User` correspondiente al nombre proporcionado.
     * @throws RuntimeException Si no se encuentra el usuario.
     */
    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
    }

    /**
     * Encuentra un estado de tarea por su nombre.
     *
     * @param statusName Nombre del estado.
     * @return Entidad `TaskStatus` correspondiente al nombre proporcionado.
     * @throws RuntimeException Si no se encuentra el estado.
     */
    private TaskStatus findTaskStatusByName(String statusName) {
        return taskStatusRepository.findByName(statusName)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + statusName));
    }

    /**
     * Encuentra una tarea por su ID y usuario asociado.
     *
     * @param taskId ID de la tarea.
     * @param user Entidad `User` asociada al propietario de la tarea.
     * @return Entidad `Task` correspondiente al ID y usuario proporcionados.
     * @throws RuntimeException Si no se encuentra la tarea.
     */
    private Task findTaskByIdAndUser(Long taskId, User user) {
        return taskRepository.findByUserId(user.getId()).stream()
                .filter(task -> taskId.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tarea con ID %d no encontrada para el usuario %s", taskId, user.getUsername())
                ));
    }

}


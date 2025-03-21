package com.previred.spa.controller;

import com.previred.spa.dto.TaskDTO;
import com.previred.spa.dto.TaskRequestDTO;
import com.previred.spa.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Endpoint para obtener la lista de tareas del usuario autenticado.
     *
     * @param userDetails Información del usuario autenticado.
     * @return Lista de tareas asociadas al usuario.
     */
    @GetMapping("/list")
    public ResponseEntity<List<TaskDTO>> getTasks(@AuthenticationPrincipal UserDetails userDetails) {
        List<TaskDTO> taskDTOs = taskService.getTasksForUser(userDetails.getUsername());
        return ResponseEntity.ok(taskDTOs);
    }

    /**
     * Endpoint para crear una nueva tarea para el usuario autenticado.
     *
     * @param taskRequestDTO DTO que contiene los datos de la tarea a crear (descripción y estado).
     * @param userDetails Información del usuario autenticado.
     * @return DTO de la tarea creada.
     */
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        TaskDTO createdTask = taskService.createTaskForUser(taskRequestDTO, userDetails.getUsername());
        return ResponseEntity.ok(createdTask);
    }

    /**
     * Endpoint para actualizar una tarea existente del usuario autenticado.
     *
     * @param id ID de la tarea a actualizar.
     * @param taskRequestDTO DTO que contiene los datos actualizados de la tarea.
     * @param userDetails Información del usuario autenticado.
     * @return DTO de la tarea actualizada.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        TaskDTO updatedTask = taskService.updateTaskForUser(id, taskRequestDTO, userDetails.getUsername());
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Endpoint para eliminar una tarea existente del usuario autenticado.
     *
     * @param id ID de la tarea a eliminar.
     * @param userDetails Información del usuario autenticado.
     * @return Respuesta HTTP sin contenido.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        taskService.deleteTaskForUser(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

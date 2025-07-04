package app.desafio.spring.boot.adapters.rest.web.impl;

import app.desafio.spring.boot.adapters.rest.dto.TaskDTO;
import app.desafio.spring.boot.adapters.rest.response.ApiResponse;
import app.desafio.spring.boot.adapters.rest.response.TaskResponse;
import app.desafio.spring.boot.adapters.rest.web.ITaskController;
import app.desafio.spring.boot.domain.model.Task;
import app.desafio.spring.boot.domain.ports.ITaskUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tareas", description = "Operaciones relacionadas con tareas")
final class TaskControllerImpl implements ITaskController {

    private final ITaskUseCase taskUseCase;

    public TaskControllerImpl(ITaskUseCase taskUseCase) {
        this.taskUseCase = taskUseCase;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            Task createdTask = taskUseCase.createTask(taskDTO.title(), taskDTO.description(), taskDTO.status());
            String formattedDate = createdTask.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            TaskResponse taskResponse = new TaskResponse(
                    createdTask.getId(),
                    createdTask.getTitle(),
                    createdTask.getDescription(),
                    formattedDate,
                    createdTask.getStatus().getStatus(),
                    createdTask.getUser().getUsername()
            );
            ApiResponse<TaskResponse> response = new ApiResponse<>("OK", "Task created successfully", taskResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error creating the task: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            Task task = taskUseCase.getTaskById(id);
            String formattedDate = task.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            TaskResponse taskResponse = new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    formattedDate,
                    task.getStatus().getStatus(),
                    task.getUser().getUsername()
            );
            ApiResponse<TaskResponse> response = new ApiResponse<>("OK", "Task found successfully", taskResponse);
            return ResponseEntity.ok(response);
        }  catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error fetching task: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTasks() {
        try {
            List<Task> tasks = taskUseCase.getAllTasks();
            List<TaskResponse> taskResponses = tasks.stream().map(task -> {
                String formattedDate = task.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                return new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        formattedDate,
                        task.getStatus().getStatus(),
                        task.getUser().getUsername()
                );
            }).collect(Collectors.toList());
            if (taskResponses.isEmpty()) {
                ApiResponse<List<TaskResponse>> response = new ApiResponse<>("NOK", "No tasks found", taskResponses);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            ApiResponse<List<TaskResponse>> response = new ApiResponse<>("OK", "Tasks found successfully", taskResponses);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error fetching tasks: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            Task updatedTask = taskUseCase.updateTask(id, taskDTO);
            String formattedDate = updatedTask.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            TaskResponse taskResponse = new TaskResponse(
                    updatedTask.getId(),
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    formattedDate,
                    updatedTask.getStatus().getStatus(),
                    updatedTask.getUser().getUsername()
            );
            ApiResponse<TaskResponse> response = new ApiResponse<>("OK", "Task updated successfully", taskResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error updating task: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskUseCase.deleteTask(id);
            ApiResponse<String> response = new ApiResponse<>("OK", "Task deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Task not found with id: " + id, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>("NOK", "Error occurred while deleting task: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

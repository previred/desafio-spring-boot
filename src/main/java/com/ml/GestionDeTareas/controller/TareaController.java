package com.ml.GestionDeTareas.controller;

import com.ml.GestionDeTareas.dao.Tarea;
import com.ml.GestionDeTareas.services.TareaServiceImpl;
import com.ml.GestionDeTareas.utils.Jwt.JwtUtil;
import com.ml.GestionDeTareas.utils.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nuevo-spa-v1")
@Tag(name="API tareas", description="CRUD de tareas")
public class TareaController {

    @Autowired
    TareaServiceImpl tareaServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Obtener todas las tareas", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas")
    })
    @GetMapping("/tareas")
    public ResponseEntity<List<Tarea>> listarTareas(){
        List<Tarea> tareas = tareaServiceImpl.listaTodasLasTareas();
        return ResponseEntity.ok(tareas);
    }

    @Operation(summary = "Crea una nueva tarea", responses = {
            @ApiResponse(responseCode = "201", description = "Json de creación"),
            @ApiResponse(responseCode = "400", description = "Ocurrió un error inesperado:")
    })
    @PostMapping("/tarea")
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea creada = tareaServiceImpl.crearTarea(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Actualiza una tarea existente", responses = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada"),
            @ApiResponse(responseCode = "400", description = "Error al actualizar la tarea::")
    })
    @PutMapping("/tarea/{id}")
    public ResponseEntity<?>actualizaTarea( @PathVariable int id,@RequestBody Tarea tarea){
        try {
            Tarea t = tareaServiceImpl.actualizarTarea(id, tarea);
            return ResponseEntity.ok("Tarea actualizada");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la tarea: " + ex.getMessage());
        }
    }

    @Operation(summary = "Eliminar una tarea", responses = {
            @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado:")
    })
    @DeleteMapping("/tarea/{id}")
    public ResponseEntity<String> eliminaTarea(@PathVariable int id) {
        tareaServiceImpl.eliminarTarea(id);
        return ResponseEntity.ok("Tarea eliminada exitosamente.");
    }
}

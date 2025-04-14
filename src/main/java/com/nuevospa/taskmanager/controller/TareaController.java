package com.nuevospa.taskmanager.controller;

import com.nuevospa.taskmanager.dto.CrearTareaRequest;
import com.nuevospa.taskmanager.dto.CrearTareaResponse;
import com.nuevospa.taskmanager.dto.ListarTareasRequest;
import com.nuevospa.taskmanager.dto.ListarTareasResponse;
import com.nuevospa.taskmanager.dto.ActualizarTareaRequest;
import com.nuevospa.taskmanager.dto.ActualizarTareaResponse;
import com.nuevospa.taskmanager.dto.EliminarTareaRequest;
import com.nuevospa.taskmanager.dto.EliminarTareaResponse;
import com.nuevospa.taskmanager.dto.TareaEliminarRequest;
import com.nuevospa.taskmanager.dto.TareaPorIdRequest;
import com.nuevospa.taskmanager.dto.RespuestaBase;
import com.nuevospa.taskmanager.service.TareaService;
import com.nuevospa.taskmanager.util.NuevoSPAParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;
    
	@Operation(
			summary = "Creacion de tareas para usuarios",
			description = "Permite crear una tarea para el usuario indicado por su id, la tarea por defecto queda en estado PE (Pendiente)"
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_OK, description = NuevoSPAParams.DESCRIPCION_OK),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_USUARIO_NO_ENCONTRADO, description = NuevoSPAParams.DESC_USUARIO_NO_ENCONTRADO),
			@ApiResponse(responseCode = NuevoSPAParams.CODIIGO_ERROR_CREAR_TAREA, description = NuevoSPAParams.DESC_ERROR_CREAR_TAREA),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_ERROR, description = NuevoSPAParams.DESCRIPCION_ERROR)
	})
    @PostMapping("/crear")
    public ResponseEntity<CrearTareaResponse> crearTarea(@RequestBody CrearTareaRequest request) {
    	
    	CrearTareaResponse response = new CrearTareaResponse();
    	response.setCodigo(NuevoSPAParams.CODIGO_OK);
    	response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);
    	
    	response = tareaService.crearTarea(request);
       
        return ResponseEntity.ok(response);
    }

	
	@Operation(
			summary = "Listado de tareas por estado",
			description = "Permite listar todas las tareas según su estado, si en el campo estadoTarea se envía con valor 'ALL', lista todas las tareas sin importar su estado"
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_OK, description = NuevoSPAParams.DESCRIPCION_OK),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_TAREAS_NO_ENCONTRADAS, description = NuevoSPAParams.DESC_TAREAS_NO_ENCONTRADAS),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_ERROR, description = NuevoSPAParams.DESCRIPCION_ERROR)
	})
    @PostMapping("/listarTareaPorEstado")
    public ResponseEntity<ListarTareasResponse> ListarTareaPorEstado(@RequestBody ListarTareasRequest request) {
    	
    	ListarTareasResponse response = new ListarTareasResponse();
    	response.setCodigo(NuevoSPAParams.CODIGO_OK);
    	response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);
    	
    	response = tareaService.listarTareasPorEstado(request);
       
        return ResponseEntity.ok(response);
    }
    
	@Operation(
			summary = "Actualizar tarea",
			description = "Permite actualizar una tarea por su id, pudiendo modificar el titulo, descripcion y estado."
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_OK, description = NuevoSPAParams.DESCRIPCION_OK),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_TAREAS_NO_ENCONTRADAS, description = NuevoSPAParams.DESC_TAREAS_NO_ENCONTRADAS),
			@ApiResponse(responseCode = NuevoSPAParams.CODIIGO_ESTADO_TAREA_NO_VALIDO, description = NuevoSPAParams.DESC_ESTADO_TAREA_NO_VALIDO),
			@ApiResponse(responseCode = NuevoSPAParams.CODIIGO_ERROR_ACTUALIZAR_TAREA, description = NuevoSPAParams.DESC_ERROR_ACTUALIZAR_TAREA),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_ERROR, description = NuevoSPAParams.DESCRIPCION_ERROR)
	})
    @PostMapping("/actualizar")
    public ResponseEntity<ActualizarTareaResponse> actualizarTarea(@RequestBody ActualizarTareaRequest request) {
    	
    	ActualizarTareaResponse response = new ActualizarTareaResponse();
    	response.setCodigo(NuevoSPAParams.CODIGO_OK);
    	response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);
    	
    	response = tareaService.actualizarTarea(request);
       
        return ResponseEntity.ok(response);
    }
    
	@Operation(
			summary = "Eliminar tarea",
			description = "Permite eliminar una tarea por su id, lo que cambia su estado a EL(Eliminada)"
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_OK, description = NuevoSPAParams.DESCRIPCION_OK),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_TAREA_NO_ENCONTRADA, description = NuevoSPAParams.DESC_TAREA_NO_ENCONTRADA),
			@ApiResponse(responseCode = NuevoSPAParams.CODIIGO_ESTADO_TAREA_NO_VALIDO, description = NuevoSPAParams.DESC_ESTADO_TAREA_NO_VALIDO),
			@ApiResponse(responseCode = NuevoSPAParams.CODIGO_ERROR, description = NuevoSPAParams.DESCRIPCION_ERROR)
	})
    @PostMapping("/eliminar")
    public ResponseEntity<EliminarTareaResponse> eliminarTarea(@RequestBody EliminarTareaRequest request) {
    	
    	EliminarTareaResponse response = new EliminarTareaResponse();
    	response.setCodigo(NuevoSPAParams.CODIGO_OK);
    	response.setDescripcion(NuevoSPAParams.DESCRIPCION_OK);
    	
    	response = tareaService.eliminarTarea(request);
       
        return ResponseEntity.ok(response);
    }
}

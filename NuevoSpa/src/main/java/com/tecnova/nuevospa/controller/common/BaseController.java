package com.tecnova.nuevospa.controller.common;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.service.common.TemplateService;
import com.tecnova.nuevospa.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

/**
 * Base that define base to be implemented in the Controllers when this contains a basic
 * transactions.<br>
 * @param <D> related DTO Response.
 * @param <R> related DTO Request.
 * @param <S> related Services.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */

public abstract class BaseController<D, R, S extends TemplateService<D, R>>{
	
	@Autowired
    protected S services;
	@Autowired
	protected ResponseUtil responseUtil;
	

	/**
	 * Create with data related.<br>
	 * 
	 * @param dto {@link DR}, This is the data to create.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Create data in the system",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Create data in the system.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@PostMapping
	public ResponseEntity<WrapperResponse<D>> create(@Valid @RequestBody R dto){
		return responseUtil.buildSuccessResponseWrapper(this.services.create(dto)).createResponse();
	}

	/**
	 * Retrieve all data.<br>
	 * 
	 * @return {@link D} of type {@link List}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Get data in the system",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Get data in the system.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@GetMapping
	public ResponseEntity<WrapperResponse<List<D>>> getAll() {
		return responseUtil.buildSuccessResponseWrapper(this.services.getAll()).createResponse();
	}

	/**
	 * Retrieve data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */

	@Operation(
			summary = "Get data in the system for id",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Get data in the system for id.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE),
					@Parameter(description = "Id of the data.", in = ParameterIn.PATH, name = "id", schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@GetMapping("/{id}")
	public ResponseEntity<WrapperResponse<D>> getById(@PathVariable(name = "id") Long id) {
		return responseUtil.buildSuccessResponseWrapper(this.services.getById(id)).createResponse();
	}

	/**
	 * Retrieve data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to find data.<br>
	 * @return {@link D}, data Found.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Get data in the system for code",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Get data in the system for code.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE),
					@Parameter(description = "code of the data.", in = ParameterIn.PATH, name = "code", schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@GetMapping("byCode/{code}")
	public ResponseEntity<WrapperResponse<D>> getByCode(@PathVariable(name = "code")String code) {
		return responseUtil.buildSuccessResponseWrapper(this.services.getByCode(code)).createResponse();
	}

	/**
	 * Update with data related.<br>
	 * 
	 * @param dto {@link DR}, This is the data to update.<br>
	 * @return {@link D}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Update data in the system",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Update data in the system.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@PutMapping
	public ResponseEntity<WrapperResponse<D>> update(@Valid @RequestBody R dto) {
		return responseUtil.buildSuccessResponseWrapper(this.services.update(dto)).createResponse();
	}
	

	
	/**
	 * Delete data for id.<br>
	 * 
	 * @param id {@link Long}, This is the id to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Delete data in the system for id",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Delete data in the system for id.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE),
					@Parameter(description = "Id of the data.", in = ParameterIn.PATH, name = "id", schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<WrapperResponse<Void>> delete(@PathVariable(name = "id") Long id) {
		this.services.delete(id);
		return responseUtil.buildSuccessResponseWrapper().createResponseVoid();
		
	}

	/**
	 * Delete data for code.<br>
	 * 
	 * @param code {@link String}, This is the code to delete data.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Delete data in the system for code",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Delete data in the system for code.",
			parameters = {
					@Parameter(description = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_DESCRIP, in = ParameterIn.HEADER, name = ConstantsApp.HEADER_TRACE_CODE, schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true, example = ConstantsApp.SWAGGER_HEADER_TRACE_CODE_EXAMPLE),
					@Parameter(description = "Code of the data.", in = ParameterIn.PATH, name = "code", schema = @Schema(type = ConstantsApp.SWAGGER_SCHEMA_STRING), required = true) 
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful operation."),
					@ApiResponse(responseCode = "400", description = "Validations not fulfilled.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "401", description = "Authentication or permissions are required.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "403", description = "You do not have permissions to perform this action.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "404", description = "No records available.", content = @Content(schema = @Schema(implementation = WrapperResponse.class))),
					@ApiResponse(responseCode = "500", description = "Unexpected error.",
							content = @Content(schema = @Schema(implementation = WrapperResponse.class)))
			}
	)
	@DeleteMapping("byCode/{code}")
	public ResponseEntity<WrapperResponse<Void>> delete(String code) {
		this.services.delete(code);
		return responseUtil.buildSuccessResponseWrapper().createResponseVoid();
		
	}
	
	
	
	
	

}

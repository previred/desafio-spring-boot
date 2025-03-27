package com.tecnova.nuevospa.security.controller;


import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.controller.common.WrapperResponse;
import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.AuthenticationsException;
import com.tecnova.nuevospa.util.ResponseUtil;
import com.tecnova.nuevospa.util.WebUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Controller in charge of managing the Endpoints in relation to the security of the system.
 * of the system.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@RestController
@RequestMapping({ "/" })
@Tag(name = "Authentication Api", description = "Api encargada de las funcionalidades relacionadas con la seguridad de la aplicación.")
@RequiredArgsConstructor
public class SecurityController {
	

	private final ResponseUtil responseUtil;
	private final WebUtil webUtil;
	
	/**
	 * Performs authentication in the system..<br>
	 * 
	 * @return {@link WrapperResponse} {@link AuthResponseDto}, user autheticated.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Operation(
			summary = "Performs authentication in the system.",
			description = ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER_METHODS+"Performs authentication in the system..",
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
	@PostMapping("/auth")
	@SecurityRequirement(name = ConstantsApp.AUTH_METHOD_BASIC)
	public ResponseEntity<WrapperResponse<AuthResponseDto>> auth() throws AuthenticationsException {
		AuthResponseDto auth = webUtil.getUser();
		if (ObjectUtils.isEmpty(auth)) {
			throw new AuthenticationsException(MessageEnum.CREDENCIALS_REQUIRED);
		}
		return responseUtil.buildSuccessResponseWrapper(auth).createResponse();
	}
}

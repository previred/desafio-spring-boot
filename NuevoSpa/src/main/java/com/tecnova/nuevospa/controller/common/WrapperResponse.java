package com.tecnova.nuevospa.controller.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * class response that request the information about the response DTO Api
 * Rest.<br>
 * 
 * @param <D> related DTO Response.
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@Schema(description = "Response template")
public class WrapperResponse<D> {
	@Schema(description = "Response statustrue:OO,false:KO).", example = "true")
	private boolean ok;
	@Schema(description = "Message generated to transation.", example = "CORRECT")
	private String message;
	@Schema(description = "Code message generated to transation.", example = "APP_001")
	private String codeMessage;
	@Schema(description = "Type of message generated to transation (INFO, WARN, ERROR).", example = "INFO")
	private String typeMessage;
	@Schema(description = "Class of message generated to transation(GENERIC,BUSINESS_ERROR,TECHNICAL_ERROR).", example = "GENERIC")
	private String classMessage;
	@Schema(description = "Trace code generated to transation.", example = "trace-009.0000")
	private String traceCode;
	@Schema(description = "result generated in the trasaction.")
	private D body;

	/**
	 * Method in charge of creating the response of the RestApi, where the Type T is the
	 * content of the body.
	 * 
	 * @return {@link ResponseEntity}<{@link WrapperResponse}<{@link List}< T >>>:
	 *         Response obtained.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public ResponseEntity<WrapperResponse<D>> createResponse() {
		return new ResponseEntity<>(this, HttpStatus.OK);
	}

	/**
	 * Method in charge of creating the response of the RestApi that its service is of type
	 * {@link void}.
	 * 
	 * @return {@link ResponseEntity}<{@link WrapperResponse}<{@link List}<{@link Void}>>>:
	 *         Response obtained.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<WrapperResponse<Void>> createResponseVoid() {
		return new ResponseEntity(this, HttpStatus.OK);
	}

	/**
	 * Method in charge of creating the response of the RestApi, where the Type T is the
	 * content of the body.
	 * 
	 * @param status {@link HttpStatus}: HttpStatus to assing.
	 * @return {@link ResponseEntity}<{@link WrapperResponse}<{@link List}< T >>>:
	 *         Response obtained.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public ResponseEntity<WrapperResponse<D>> createResponse(HttpStatus status) {
		return new ResponseEntity<>(this, status);
	}

}

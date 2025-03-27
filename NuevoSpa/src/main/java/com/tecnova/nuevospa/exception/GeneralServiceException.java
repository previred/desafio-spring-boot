package com.tecnova.nuevospa.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.tecnova.nuevospa.enums.MessageEnum;

import lombok.Getter;
import lombok.Setter;
/**
 * This class is in charge of containing the relevant information of the
 * exceptions of the type
 * {@link GeneralServiceException}, presented in the system.<br>
  * @author </br>
  * Developer: Arnold Campillo
 */
@Getter
@Setter
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class GeneralServiceException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	private final MessageEnum error;
	
	 /**
     * Constructor in charge of initializing the class variables.</br>
     * @param error del tipo {@link MensajesEnum}:Error Message.
     * @param cause del tipo {@link Throwable}: Error throwable.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
     */	
	public GeneralServiceException(MessageEnum error, Throwable cause) {
		super(error.getCode(), cause);
		this.error=error;
	}

	
}

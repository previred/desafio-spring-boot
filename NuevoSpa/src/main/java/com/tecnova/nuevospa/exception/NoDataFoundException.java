package com.tecnova.nuevospa.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.tecnova.nuevospa.enums.MessageEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is in charge of containing the relevant information of the
 * exceptions of the type
 * {@link NoDataFoundException}, presented in the system.<br>
  * @author </br>
  * Developer: Arnold Campillo
 */
@Getter
@Setter
@Builder
@ResponseStatus(code= HttpStatus.NO_CONTENT)
public class NoDataFoundException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	private final MessageEnum error;
	private final transient Object[] paramMessage;
	

	/**
	 * Constructor in charge of initializing the class variables.</br>
	 * 
	 * @param error del tipo {@link MessageEnum}:error message.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public NoDataFoundException(MessageEnum error) {
		super(error.getCode());
		this.error=error;
		this.paramMessage=null;
	}
	
	/**
	 * Constructor in charge of initializing the class variables.</br>
	 * 
	 * @param error        {@link MessageEnum}: Error message.
	 * @param paramMessage del tipo {@link Object}[]: Error params({@link MessageEnum}).
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */	
	public NoDataFoundException(MessageEnum error,Object... paramMessage) {
		super(error.getCode());
		this.error=error;
		this.paramMessage=paramMessage;
	}
}

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
 * authentication and authorization exceptions of the type
 * {@link AuthenticationException}, presented in the system.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Getter
@Setter
@Builder
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AuthenticationsException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	private final MessageEnum error;
	private final transient Object[] paramMessage;

	/**
	 * Constructor in charge of initializing the class variables.</br>
	 * 
	 * @param error        {@link MessageEnum}: Error message.
	 * @param paramMessage del tipo {@link Object}[]: Error params({@link MessageEnum}).
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public AuthenticationsException(MessageEnum error, Object... paramMessage) {
		super(error.getCode());
		this.error = error;
		this.paramMessage = paramMessage;
	}

	/**
	 * Constructor in charge of initializing the class variables.</br>
	 * 
	 * @param error del tipo {@link MessageEnum}:error message.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public AuthenticationsException(MessageEnum error) {
		super(error.getCode());
		this.error = error;
		this.paramMessage = null;
	}

}

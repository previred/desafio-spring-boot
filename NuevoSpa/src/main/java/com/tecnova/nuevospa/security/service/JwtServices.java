package com.tecnova.nuevospa.security.service;

import java.util.Date;

import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.exception.ValidateServiceException;

/**
 * Intefaz Service in charge of performing JWT transactions and operations.
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface JwtServices {

	/**
	 * Method responsible for creating access tokens.
	 * 
	 * @param user {@link AuthResponseDto}: User to assign the token.
	 * @return user {@link AuthResponseDto}: User Configured with tokens.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public AuthResponseDto createToken(AuthResponseDto user);

	/**
	 * Method in charge of obtaining the user of the token.
	 * 
	 * @param token {@link String}: access token to be validated.
	 * @return result @link AuthResponseDto}:user obtained from token
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	public AuthResponseDto getUserFromToken(String token) throws ValidateServiceException;
	
	
	/**
	 * Method in charge of obtaining the expired date of the token.
	 * 
	 * @param token {@link String}: access token to be validated.
	 * @return result @link {@link Date}}:Expired obtained from token
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	public Date getDateExpiredFromToken(String token) throws ValidateServiceException;
	
	 /**
	 * Method in charge of validating the access token.
	 * @param token{@link String}: token to validate
	 * @return result {@link boolean}: result of validation.   
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	public boolean validateAccessToken(String token);

}

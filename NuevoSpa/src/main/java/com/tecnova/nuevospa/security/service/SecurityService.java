package com.tecnova.nuevospa.security.service;

import com.tecnova.nuevospa.dto.response.AuthResponseDto;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Services that manages the transactions about the authentications.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface SecurityService {

	/**
	 * Performs user authentication.<br>
	 * 
	 * @param username {@link String}, This is the username for the
	 *                 authentication.<br>
	 * @param password {@link String}, This is the password for the
	 *                 authentication.<br>
	 * @return {@link AuthResponseDto} data obtained.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	AuthResponseDto authenticate(String username, String password);
	
	/**
	 * validate token.<br>
	 * 
	 * @param username {@link String}, This is the username for the
	 *                 authentication.<br>
	 * @param token {@link String}, This is the token for the
	 *                 authentication.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	void validateTokenUser(String username, String token);
	
	
	 /**
	 * Method in charge of assigning the user to the authentication context {@link SecurityContextHolder}.
	 * @param token {@link String}: token equired for the request. 
	 * @param request  {@link HttpServletRequest}: Request required for the request.
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 * @throws
	 */
	public void setAuthenticationContext(String token, HttpServletRequest request);

}

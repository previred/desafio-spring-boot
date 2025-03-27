package com.tecnova.nuevospa.service;


import java.util.Optional;

import com.tecnova.nuevospa.dto.response.UserResponseDto;
import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.service.common.TemplateService;

/**
 * Services that manages the transactions about the User {@link User}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface UserService extends TemplateService<UserResponseDto, UserResponseDto>{

	/**
	 * Find data with the username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	public UserResponseDto findByUsername(String username);
	
	

	/**
	 * Find data with the token.<br>
	 * 
	 * @param token {@link String}, This is the token for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	public UserResponseDto findByToken(String token);
	
	/**
	 * Find data with the code.<br>
	 * 
	 * @param code {@link String}, This is the code for the query.<br
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	public User findUserByCode(String code);
	
	
	
	/**
	 * Find data with the username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	public User findUserByUsername(String username);
	
	/**
	 * Registry token for username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br>
	 * @param token {@link String}, This is the token for the query.<br>
	 */
	public void registryToken(String username, String token);
}

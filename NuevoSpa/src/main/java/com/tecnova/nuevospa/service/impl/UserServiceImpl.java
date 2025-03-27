package com.tecnova.nuevospa.service.impl;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tecnova.nuevospa.dto.response.UserResponseDto;
import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.NoDataFoundException;
import com.tecnova.nuevospa.mapper.UserMapper;
import com.tecnova.nuevospa.repository.UserRepository;
import com.tecnova.nuevospa.service.UserService;
import com.tecnova.nuevospa.service.common.BaseService;
/**
 * Services that manages the transactions about the User {@link User}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service

public class UserServiceImpl extends BaseService<UserResponseDto, UserResponseDto, User, UserMapper, UserRepository> implements UserService{
	

	/**
	 * Find data with the username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	@Override
	public UserResponseDto findByUsername(String username) {
		var user = this.findUserByUsername(username);
		return mapper.toDto(user);
	}

	/**
	 * Find data with the username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	@Override
	public User findUserByUsername(String username) {
		return repository.findByUsername(username).orElseThrow(()->new NoDataFoundException(MessageEnum.USER_NO_FOUND, username));
	}

	/**
	 * Registry token for username.<br>
	 * 
	 * @param username {@link String}, This is the username for the query.<br>
	 * @param token {@link String}, This is the token for the query.<br>
	 */
	@Override
	public void registryToken(String username, String token) {
		var user = this.findUserByUsername(username);
		user.setTokenAccess(token);
		repository.save(user);
		
	}

	 /* Find data with the token.<br>
	 * 
	 * @param token {@link String}, This is the token for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	@Override
	public UserResponseDto findByToken(String token) {
		return mapper.toDto(repository.findByTokenAccess(token).orElseThrow(()->new NoDataFoundException(MessageEnum.DATA_NO_FOUND)));
	}

	 /* Find data with the code.<br>
	 * 
	 * @param code {@link String}, This is the code for the query.<br>
	 * @return {@link Optional} of type {@link User}, data obtained.<br>
	 */
	@Override
	public User findUserByCode(String code) {
		return repository.findByCode(code).orElseThrow(()->new NoDataFoundException(MessageEnum.DATA_NO_FOUND));
	}
	
}

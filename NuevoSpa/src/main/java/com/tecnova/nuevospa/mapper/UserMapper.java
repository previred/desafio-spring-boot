package com.tecnova.nuevospa.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import com.tecnova.nuevospa.dto.response.AuthResponseDto;
import com.tecnova.nuevospa.dto.response.UserResponseDto;
import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.mapper.common.BaseMapper;
/**
 * Defines the way in which objects should be converted between model - DTO -
 * model for {@link User}.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
public class UserMapper implements BaseMapper<User, UserResponseDto, UserResponseDto>{

	/**
	 * Mapper a Model object to its respective DTO.<br>
	 * 
	 * @param entity [{@link User}], This is the Model.<br>
	 * @return {@link UserResponseDto}, DTO Response corresponding to the Entity.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public UserResponseDto toDto(User entity) {
		
		return ObjectUtils.isEmpty(entity) ? null: 
			UserResponseDto.builder()
			.code(entity.getCode())
			.username(entity.getUsername())
			.build();
	}

	/**
	 * Mapper a DTO object to its respective Model.<br>
	 * 
	 * @param dto [{@link UserResponseDto}], This is the DTO Request.<br>
	 * @return {@link User}, Model corresponding to the DTO.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public User toModel(UserResponseDto dto) {
		return ObjectUtils.isEmpty(dto) ? null: 
			User.builder()
			.code(dto.code())
			.username(dto.username())
			.build();
	}
	
	/**
	 * Mapper a Model object to its respective Auth DTO.<br>
	 * 
	 * @param entity [{@link User}], This is the Model.<br>
	 * @return {@link AuthResponseDto}, DTO Response corresponding to the Entity.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	public AuthResponseDto toAuthResponse(User entity) {
		return ObjectUtils.isEmpty(entity) ? null: 
			AuthResponseDto.builder()
			.code(entity.getCode())
			.username(entity.getUsername())
			.tokenAccess(entity.getTokenAccess())
			.build();
	}
	

}

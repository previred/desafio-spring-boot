package com.tecnova.nuevospa.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import com.tecnova.nuevospa.dto.response.StatusTaskResponseDto;
import com.tecnova.nuevospa.entity.StatusTask;
import com.tecnova.nuevospa.mapper.common.BaseMapper;
/**
 * Defines the way in which objects should be converted between model - DTO -
 * model for {@link StatusTask}.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
public class StatusTaskMapper implements BaseMapper<StatusTask, StatusTaskResponseDto, StatusTaskResponseDto>{

	/**
	 * Mapper a Model object to its respective DTO.<br>
	 * 
	 * @param entity [{@link StatusTask}], This is the Model.<br>
	 * @return {@link StatusTaskResponseDto}, DTO Response corresponding to the Entity.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public StatusTaskResponseDto toDto(StatusTask entity) {
		
		return ObjectUtils.isEmpty(entity) ? null: 
			StatusTaskResponseDto.builder()
			.code(entity.getCode())
			.name(entity.getName())
			.build();
	}

	/**
	 * Mapper a DTO object to its respective Model.<br>
	 * 
	 * @param dto [{@link StatusTaskResponseDto}], This is the DTO Request.<br>
	 * @return {@link StatusTask}, Model corresponding to the DTO.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public StatusTask toModel(StatusTaskResponseDto dto) {
		return ObjectUtils.isEmpty(dto) ? null: 
			StatusTask.builder()
			.code(dto.code())
			.name(dto.name())
			.build();
	}
	

	

}

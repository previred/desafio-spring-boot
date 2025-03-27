package com.tecnova.nuevospa.mapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import com.tecnova.nuevospa.dto.request.TaskRequestDto;
import com.tecnova.nuevospa.dto.response.StatusTaskResponseDto;
import com.tecnova.nuevospa.dto.response.TaskResponseDto;
import com.tecnova.nuevospa.entity.Task;
import com.tecnova.nuevospa.mapper.common.BaseMapper;

import lombok.RequiredArgsConstructor;
/**
 * Defines the way in which objects should be converted between model - DTO -
 * model for {@link Task}.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Component
@RequiredArgsConstructor
public class TaskMapper implements BaseMapper<Task, TaskRequestDto, TaskResponseDto>{

	private final UserMapper userMapper;
	private final StatusTaskMapper statusTaskMapper;
	/**
	 * Mapper a Model object to its respective DTO.<br>
	 * 
	 * @param entity [{@link Task}], This is the Model.<br>
	 * @return {@link TaskResponseDto}, DTO Response corresponding to the Entity.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public TaskResponseDto toDto(Task entity) {
		
		return ObjectUtils.isEmpty(entity) ? null: 
			TaskResponseDto.builder()
			.id(entity.getId())
			.code(entity.getCode())
			.name(entity.getName())
			.description(entity.getDescription())
			.startTask(entity.getStartTask())
			.endTask(entity.getEndTask())
			.user(userMapper.toDto(entity.getUser()))
			.statusTask(statusTaskMapper.toDto(entity.getStatusTask()))
			.build();
	}

	/**
	 * Mapper a DTO object to its respective Model.<br>
	 * 
	 * @param dto [{@link StatusTaskResponseDto}], This is the DTO Request.<br>
	 * @return {@link Task}, Model corresponding to the DTO.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	public Task toModel(TaskRequestDto dto) {
		return ObjectUtils.isEmpty(dto) ? null: 
			Task.builder()
			.id(dto.id())
			.name(dto.name())
			.description(dto.description())
			.startTask(dto.startTask())
			.endTask(dto.endTask())
			.build();
	}
	

	

}

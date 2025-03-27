package com.tecnova.nuevospa.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import com.tecnova.nuevospa.dto.request.TaskRequestDto;
import com.tecnova.nuevospa.dto.response.TaskResponseDto;
import com.tecnova.nuevospa.entity.StatusTask;
import com.tecnova.nuevospa.entity.Task;
import com.tecnova.nuevospa.entity.User;
import com.tecnova.nuevospa.enums.MessageEnum;
import com.tecnova.nuevospa.exception.NoDataFoundException;
import com.tecnova.nuevospa.exception.ValidateServiceException;
import com.tecnova.nuevospa.mapper.TaskMapper;
import com.tecnova.nuevospa.repository.StatusTaskRepository;
import com.tecnova.nuevospa.repository.TaskRepository;
import com.tecnova.nuevospa.service.TaskService;
import com.tecnova.nuevospa.service.UserService;
import com.tecnova.nuevospa.service.common.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Services that manages the transactions about the Task {@link Task}.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends BaseService<TaskResponseDto, TaskRequestDto, Task, TaskMapper, TaskRepository>
		implements TaskService {

	private final UserService userService;
	private final StatusTaskRepository statusTaskRepository;


	/**
	 * Update with data related.<br>
	 * 
	 * @param dto {@link TaskRequestDto}, This is the data to update.<br>
	 * @return {@link TaskResponseDtoD}, data Updated.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	
	@Override
	@Transactional
	public TaskResponseDto update(TaskRequestDto dto) {
		if (ObjectUtils.isEmpty(dto.id())) {
			throw new ValidateServiceException(MessageEnum.ID_REQUIRED);
		}

		Task task = this.mapper.toModel(dto);

		StatusTask statusTask = statusTaskRepository.findByCode(dto.statusTaskCode())
				.orElseThrow(() -> new NoDataFoundException(MessageEnum.DATA_NO_FOUND));

		User user = userService.findUserByCode(dto.userCode());

		task.setUser(user);
		task.setStatusTask(statusTask);

		Task taskSaved = this.repository.save(task);
		return this.mapper.toDto(taskSaved);
	}
	
	/**
	 * Create with data related.<br>
	 * 
	 * @param dto {@link TaskRequestDto}, This is the data to create.<br>
	 * @return {@link TaskResponseDtoD}, data created.<br>
	 * @author GCR.</br>
	 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
	 */
	@Override
	@Transactional
	public TaskResponseDto create(TaskRequestDto dto) {
		Task task = this.mapper.toModel(dto);

		StatusTask statusTask = statusTaskRepository.findByCode(dto.statusTaskCode())
				.orElseThrow(() -> new NoDataFoundException(MessageEnum.DATA_NO_FOUND));

		User user = userService.findUserByCode(dto.userCode());

		task.setUser(user);
		task.setStatusTask(statusTask);

		Task taskSaved = this.repository.save(task);
		return this.mapper.toDto(taskSaved);
	}

}

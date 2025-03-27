package com.tecnova.nuevospa.service;

import com.tecnova.nuevospa.dto.request.TaskRequestDto;
import com.tecnova.nuevospa.dto.response.TaskResponseDto;
import com.tecnova.nuevospa.service.common.TemplateService;

/**
 * Services that manages the transactions about the Task {@link Task}.<br>
 * 
 * @author GCR.</br>
 *          Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
public interface TaskService extends TemplateService<TaskResponseDto, TaskRequestDto>{

}

package com.tecnova.nuevospa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tecnova.nuevospa.constant.ConstantsApp;
import com.tecnova.nuevospa.controller.common.BaseController;
import com.tecnova.nuevospa.dto.request.TaskRequestDto;
import com.tecnova.nuevospa.dto.response.TaskResponseDto;
import com.tecnova.nuevospa.service.TaskService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller that manages the information about the tasks.<br>
 * 
 * @author GCR.</br>
 *         Developer: Giovanny Camacho[giovannycr004@gmail.com]
 */
@RestController
@RequestMapping({ "/task" })
@PreAuthorize("hasAnyRole('ROLE_USER')")
@SecurityRequirement(name = "bearer")
@Tag(name = "Task Api", description = "Api Responsible for administering the application to Taks."
		+ ConstantsApp.MSG_OPENAPI_GENERIC_CONTROLLER)
public class TaskController extends BaseController<TaskResponseDto, TaskRequestDto, TaskService>{

}

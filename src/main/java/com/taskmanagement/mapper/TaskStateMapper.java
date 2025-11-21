package com.taskmanagement.mapper;

import com.taskmanagement.dto.response.TaskStateResponse;
import com.taskmanagement.entity.TaskState;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStateMapper {

    TaskStateResponse toResponse(TaskState taskState);
}

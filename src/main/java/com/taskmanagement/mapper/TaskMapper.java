package com.taskmanagement.mapper;

import com.taskmanagement.dto.request.CreateTaskRequest;
import com.taskmanagement.dto.response.TaskResponse;
import com.taskmanagement.entity.Task;
import com.taskmanagement.entity.TaskState;
import com.taskmanagement.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TaskStateMapper.class, UserMapper.class}, builder = @Builder(disableBuilder = true))
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "dueDate", source = "request.dueDate")
    Task toEntity(CreateTaskRequest request, User user, TaskState state);

    @Mapping(target = "assignedUser", source = "user")
    @Mapping(target = "createdBy", source = "user")
    TaskResponse toResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CreateTaskRequest request, @MappingTarget Task task);
}

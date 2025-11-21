package com.taskmanagement.service;

import com.taskmanagement.dto.response.TaskStateResponse;
import com.taskmanagement.mapper.TaskStateMapper;
import com.taskmanagement.repository.TaskStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskStateService {

    private final TaskStateRepository taskStateRepository;
    private final TaskStateMapper taskStateMapper;

    public TaskStateService(TaskStateRepository taskStateRepository, TaskStateMapper taskStateMapper) {
        this.taskStateRepository = taskStateRepository;
        this.taskStateMapper = taskStateMapper;
    }

    public List<TaskStateResponse> getAllTaskStates() {
        return taskStateRepository.findAll().stream()
                .map(taskStateMapper::toResponse)
                .toList();
    }
}

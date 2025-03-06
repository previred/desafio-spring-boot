package com.provired.challenge.service;

import com.provired.challenge.dto.TaskStatusDTO;
import com.provired.challenge.entity.TaskStatus;
import com.provired.challenge.exception.ResourceNotFoundException;
import com.provired.challenge.repository.TaskStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public TaskStatusService(TaskStatusRepository taskStatusRepository) {
        this.taskStatusRepository = taskStatusRepository;
    }

    @Transactional
    public List<TaskStatusDTO> getAllStatuses() {
        return taskStatusRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskStatusDTO getStatusById(Long id) {
        return taskStatusRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado con ID: " + id));
    }

    @Transactional
    public TaskStatusDTO createStatus(TaskStatusDTO dto) {
        var status = TaskStatus.builder().name(dto.getName()).build();
        return convertToDTO(taskStatusRepository.save(status));
    }

    @Transactional
    public TaskStatusDTO updateStatus(Long id, TaskStatusDTO dto) {
        var status = taskStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado con ID: " + id));

        status.setName(dto.getName());
        return convertToDTO(taskStatusRepository.save(status));
    }

    @Transactional
    public void deleteStatus(Long id) {
        if (!taskStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estado no encontrado con ID: " + id);
        }
        taskStatusRepository.deleteById(id);
    }

    private TaskStatusDTO convertToDTO(TaskStatus status) {
        return new TaskStatusDTO(status.getId(), status.getName());
    }
}
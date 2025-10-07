package com.nuevo.taskmanager.services;


import com.nuevo.taskmanager.entities.StatusEntity;
import com.nuevo.taskmanager.model.Status;
import com.nuevo.taskmanager.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatusService {

    private final StatusRepository statusRepository;

    public List<Status> getAllStatuses() {
        log.info("Obteniendo todos los estados de tareas");
        List<Status> result = statusRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Se encontraron {} estados", result.size());
        return result;
    }

    private Status toDto(StatusEntity entity) {
        Status dto = new Status();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}

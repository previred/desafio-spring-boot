package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.entity.TaskStatus;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.repository.TaskStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusService {
    private final TaskStatusRepository repository;

    public TaskStatusService(TaskStatusRepository repository) {
        this.repository = repository;
    }

    public TaskStatus findByName(String statusName) {
        return repository.findByNameIgnoreCase(statusName)
                .orElseThrow(() -> new ResourceNotFoundException("Estado no encontrado"));
    }
}


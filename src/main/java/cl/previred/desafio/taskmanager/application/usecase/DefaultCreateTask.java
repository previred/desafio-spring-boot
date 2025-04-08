package cl.previred.desafio.taskmanager.application.usecase;

import cl.previred.desafio.taskmanager.domain.exceptions.TaskException;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.CreateTaskRepository;
import cl.previred.desafio.taskmanager.domain.usecase.CreateTaskUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultCreateTask implements CreateTaskUseCase {

    private final CreateTaskRepository createTaskRepository;

    @Override
    public Task execute(Task task) {
        try {
            log.debug("Creating task: {}", task);
            Task createdTask = createTaskRepository.save(task);
            log.info("Task created successfully: {}", createdTask);
            return createdTask;
        } catch (Exception e) {
            log.error("Failed to create task", e);
            throw new TaskException("Could not create task: "+ task);
        }
    }
}

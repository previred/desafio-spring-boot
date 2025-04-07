package cl.previred.desafio.taskmanager.application.usecase;

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
        Task createdTask = createTaskRepository.save(task);
        log.info("CREATED TASK: {}",createdTask);
        return createdTask;
    }
}

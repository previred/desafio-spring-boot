package cl.previred.desafio.taskmanager.application.usecase;

import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.UpdateTaskRepository;
import cl.previred.desafio.taskmanager.domain.usecase.UpdateTaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUpdateTask implements UpdateTaskUseCase {

    private final UpdateTaskRepository updateTaskRepository;

    @Override
    public Task execute(String id, Task task) {
        return updateTaskRepository.update(id,task);
    }
}

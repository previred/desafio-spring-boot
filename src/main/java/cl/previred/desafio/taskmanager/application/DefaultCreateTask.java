package cl.previred.desafio.taskmanager.application;

import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.usecase.CreateTaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCreateTask implements CreateTaskUseCase {

    @Override
    public Task execute(Task task) {
        return null;
    }
}

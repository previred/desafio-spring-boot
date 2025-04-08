package cl.previred.desafio.taskmanager.application.usecase;

import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.RetrieveTaskRepository;
import cl.previred.desafio.taskmanager.domain.usecase.RetrieveTaskUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DefaultRetrieveTask implements RetrieveTaskUseCase {

    private final RetrieveTaskRepository retrieveTaskRepository;

    @Override
    public Task execute(String id) {
        return retrieveTaskRepository.retrieve(id);
    }
}

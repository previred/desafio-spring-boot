package cl.previred.desafio.taskmanager.application.usecase;

import cl.previred.desafio.taskmanager.domain.ports.out.DeleteTaskRepository;
import cl.previred.desafio.taskmanager.domain.usecase.DeleteTaskUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDeleteTask implements DeleteTaskUseCase {

    private final DeleteTaskRepository deleteTaskRepository;

    @Override
    public boolean execute(String id) {
        return deleteTaskRepository.delete(id);
    }
}

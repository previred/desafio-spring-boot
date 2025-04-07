package cl.previred.desafio.taskmanager.application.usecase;

import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.ports.out.ListTasksRepository;
import cl.previred.desafio.taskmanager.domain.usecase.ListTasksUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultListTasks implements ListTasksUseCase {

    private final ListTasksRepository listTasksRepository;

    @Override
    public List<Task> execute() {
        return listTasksRepository.retrieve();
    }
}

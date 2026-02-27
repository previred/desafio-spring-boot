package cl.previred.taskmanager.infrastructure.persistence.adapter;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.repository.TaskRepository;
import cl.previred.taskmanager.infrastructure.persistence.entity.TaskEntity;
import cl.previred.taskmanager.infrastructure.persistence.entity.TaskStatusEntity;
import cl.previred.taskmanager.infrastructure.persistence.entity.UserEntity;
import cl.previred.taskmanager.infrastructure.persistence.mapper.TaskMapper;
import cl.previred.taskmanager.infrastructure.persistence.repository.TaskJpaRepository;
import cl.previred.taskmanager.infrastructure.persistence.repository.TaskStatusJpaRepository;
import cl.previred.taskmanager.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskStatusJpaRepository taskStatusJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public TaskRepositoryAdapter(
            TaskJpaRepository taskJpaRepository,
            TaskStatusJpaRepository taskStatusJpaRepository,
            UserJpaRepository userJpaRepository
    ) {
        this.taskJpaRepository = taskJpaRepository;
        this.taskStatusJpaRepository = taskStatusJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Task save(Task task) {

        // Resolver estado (catálogo precargado)
        TaskStatusEntity statusEntity =
                taskStatusJpaRepository.findByName(task.status().name())
                        .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));

        // Resolver usuario
        UserEntity userEntity =
                userJpaRepository.findById(task.user().id())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Crear entidad
        TaskEntity entity = new TaskEntity(
                task.id(),
                task.title(),
                task.description(),
                statusEntity,
                userEntity,
                task.createdAt()
        );

        TaskEntity saved = taskJpaRepository.save(entity);

        return TaskMapper.toDomain(saved);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskJpaRepository.findById(id)
                .map(TaskMapper::toDomain);
    }

    @Override
    public List<Task> findAll() {
        return TaskMapper.toDomainList(taskJpaRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        taskJpaRepository.deleteById(id);
    }
}
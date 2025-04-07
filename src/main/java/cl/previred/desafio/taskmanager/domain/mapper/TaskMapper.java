package cl.previred.desafio.taskmanager.domain.mapper;


import cl.previred.desafio.taskmanager.domain.model.Task;

public interface TaskMapper<T extends Task, E> {
    E toEntity(T task);
    T toDomain(E entity);
}
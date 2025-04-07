package cl.previred.desafio.domain.ports.in.usecases.tarea;

public interface IAssignTareaUseCase {
    void execute(Long tareaId, Long usuarioId);
}

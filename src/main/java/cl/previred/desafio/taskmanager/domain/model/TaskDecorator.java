package cl.previred.desafio.taskmanager.domain.model;

public abstract class TaskDecorator implements Task {
    protected Task decoratedTask;

    public TaskDecorator(Task task) {
        this.decoratedTask = task;
    }
}

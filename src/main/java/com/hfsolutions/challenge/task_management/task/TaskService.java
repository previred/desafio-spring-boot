package com.hfsolutions.challenge.task_management.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserEntity;
import com.hfsolutions.challenge.task_management.auth.jwt.repository.UserRepository;
import com.hfsolutions.challenge.task_management.openapi.model.TaskProperties;
import com.hfsolutions.challenge.task_management.task.repository.TaskEntity;
import com.hfsolutions.challenge.task_management.task.repository.TaskRepository;
import com.hfsolutions.challenge.task_management.task.repository.TaskStatusEntity;
import com.hfsolutions.challenge.task_management.task.repository.TaskStatusRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;

    @Transactional
    public TaskEntity createTask(TaskEntity task, String userEmail) throws Exception {

        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            task.setUserId(UUID.fromString(user.get().getId()));
            TaskStatusEntity taskStatus = taskStatusRepository.findById(1).get();
            task.setStatus(taskStatus);
            taskRepository.save(task);
            return task;
        }
        throw new Exception("Error identifying the user");
    }

    public List<TaskEntity> getAllTask(String userEmail) throws Exception {
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            return taskRepository.findByUserId(UUID.fromString(user.get().getId()));
        }
        throw new Exception("Error identifying the user");

    }

    @Transactional
    public void taskStatus(String userEmail, UUID taskId, Integer statusId) {
        TaskEntity task = this.getTask(taskId, userEmail);
        TaskStatusEntity status = taskStatusRepository.findById(statusId).get();
        task.setStatus(status);
        taskRepository.save(task);
        return;
    }

    public TaskEntity getTask(UUID taskId, String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail).get();
        return taskRepository.findByIdAndUserId(taskId, UUID.fromString(user.getId())).get();
    }

    @Transactional
    public TaskEntity updateTask(String userEmail, UUID taskId, TaskProperties taskProperties) {
        TaskEntity taskEntity = this.getTask(taskId, userEmail);
        taskEntity.setDescription(taskProperties.getDescription());
        taskEntity.setTitle(taskProperties.getTitle());
        taskRepository.save(taskEntity);
        return taskEntity;
    }

    public void taskTaskIdDelete(String userEmail, UUID taskId) {
        TaskEntity taskEntity = this.getTask(taskId, userEmail);
        taskRepository.delete(taskEntity);
    }

}

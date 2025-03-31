package com.hfsolutions.challenge.task_management.task;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hfsolutions.challenge.task_management.auth.jwt.JwtAuthenticationFilter;
import com.hfsolutions.challenge.task_management.auth.jwt.JwtTokenProvider;
import com.hfsolutions.challenge.task_management.openapi.TaskApiDelegate;
import com.hfsolutions.challenge.task_management.openapi.model.Task;
import com.hfsolutions.challenge.task_management.openapi.model.TaskProperties;
import com.hfsolutions.challenge.task_management.task.repository.TaskEntity;
import com.hfsolutions.challenge.task_management.task.repository.TaskMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskApiDelegateImpl implements TaskApiDelegate {

    private final TaskService taskService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TaskMapper taskMapper;

    @Override
    public ResponseEntity<Task> createTask(String authorization,
            TaskProperties taskProperties) {
        try {

            String user = getUser(authorization);
            TaskEntity task = taskService.createTask(taskMapper.toEntity(taskProperties), user);
            return new ResponseEntity<Task>(task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Task>> getAllTask(String authorization) {
        try {
            String user = getUser(authorization);
            List<TaskEntity> tasks = taskService.getAllTask(user);
            List<Task> tasksDto = tasks.stream().map(t -> {
                return taskMapper.toDto(t);
            }).collect(Collectors.toList());
            return new ResponseEntity<List<Task>>(tasksDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Task> getTask(String authorization,
            UUID taskId) {
        try {
            String user = getUser(authorization);
            TaskEntity taskEntity = taskService.getTask(taskId, user);
            return new ResponseEntity<Task>(taskMapper.toDto(taskEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> taskStatus(String authorization,
            UUID taskId,
            Integer statusId) {
        try {
            String user = getUser(authorization);
            taskService.taskStatus(user, taskId, statusId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTask(String authorization, UUID taskId) {
        try {
            String user = getUser(authorization);
            taskService.taskTaskIdDelete(user, taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Task> updateTask(String authorization, UUID taskId, TaskProperties taskProperties) {
        try {
            String user = getUser(authorization);
            TaskEntity taskEntity = taskService.updateTask(user, taskId, taskProperties);
            return new ResponseEntity<>(taskMapper.toDto(taskEntity), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String getUser(String authorization) throws Exception {
        try {
            String token = jwtAuthenticationFilter.extractToken(authorization);
            String user = jwtTokenProvider.getUsernameFromJWT(token);
            return user;
        } catch (Exception e) {
            throw new Exception("Invalid authorization");
        }
    }

}

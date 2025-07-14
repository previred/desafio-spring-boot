package com.previred.task_manager.controllers;

import com.previred.api.spec.controller.TareasApi;
import com.previred.api.spec.dto.ActualizaTarea;
import com.previred.api.spec.dto.NuevaTarea;
import com.previred.api.spec.dto.Tarea;
import com.previred.task_manager.dto.user.UserRespDto;
import com.previred.task_manager.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class TaskController implements TareasApi {

    private final ITaskService taskService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(ITaskService userService) {
        this.taskService = userService;
    }

    public ResponseEntity<Tarea> tareasIdGet(String id) {
        log.info("TaskController.tareasIdGet {}",id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }



    @Override
    public ResponseEntity<List<Tarea>> apiV1TasksGet() {
        return null;
    }

    @Override
    public ResponseEntity<Void> apiV1TasksIdDelete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Tarea> apiV1TasksIdGet(String id) {
        log.info("TaskController.tareasIdGet {}",id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Override
    public ResponseEntity<Tarea> apiV1TasksIdPut(String id, ActualizaTarea actualizaTarea) {
        return null;
    }

    @Override
    public ResponseEntity<Tarea> apiV1TasksPost(NuevaTarea nuevaTarea) {
        return null;
    }
}
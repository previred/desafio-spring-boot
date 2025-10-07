package com.nuevo.taskmanager.controller;


import com.nuevo.taskmanager.model.Status;
import com.nuevo.taskmanager.services.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-status")
@RequiredArgsConstructor
@Log4j2
public class TaskApiStatusController {

    private final StatusService statusService;

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        log.info("Petición para obtener todos los estados de tarea");
        List<Status> statuses = statusService.getAllStatuses();
        log.info("Se retornaron {} estados", statuses.size());
        return ResponseEntity.ok(statuses);
    }
}

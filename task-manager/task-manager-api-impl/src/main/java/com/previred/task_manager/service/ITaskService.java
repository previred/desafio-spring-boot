package com.previred.task_manager.service;

import com.previred.api.spec.dto.Tarea;

import java.util.UUID;

public interface ITaskService {
  Tarea getTaskById(String id);

}

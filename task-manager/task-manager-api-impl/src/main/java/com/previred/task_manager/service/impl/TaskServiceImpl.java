package com.previred.task_manager.service.impl;

import com.previred.api.spec.dto.Tarea;
import com.previred.task_manager.components.exceptions.GeneralException;
import com.previred.task_manager.domain.model.Task;
import com.previred.task_manager.domain.repositories.ITaskRepository;
import com.previred.task_manager.dto.task.TaskRespDto;
import com.previred.task_manager.dto.user.UserReqDto;
import com.previred.task_manager.service.ITaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {
    private final ITaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);


    @Override
    public Tarea getTaskById(String id) {
        log.info("TaskServiceImpl.getTaskById {}", id);
        Task taskFound =  taskRepository.findById(UUID.fromString(id)).orElseThrow(
                () ->  new GeneralException("No encontro data con ese identificador", HttpStatus.NOT_FOUND));
        return convertToTarea(taskFound);
    }

    public Tarea convertToTarea(Task entity){
        Tarea tarea= modelMapper.map(entity, Tarea.class);
        tarea.setUsuario(entity.getUser().getNombre());
        tarea.setEstadoTarea(entity.getTaskState().getNombre());
        return tarea;
    }
}

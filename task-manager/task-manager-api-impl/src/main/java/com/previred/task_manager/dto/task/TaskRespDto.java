package com.previred.task_manager.dto.task;

import com.previred.task_manager.domain.model.TaskState;
import com.previred.task_manager.domain.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TaskRespDto {

    private Integer id;

    private String name;

    private String description;

    private User user;

    private TaskState taskState;

}

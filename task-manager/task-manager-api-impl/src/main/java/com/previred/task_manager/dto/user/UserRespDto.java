package com.previred.task_manager.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.previred.task_manager.components.constants.GeneralField.LOCAL_DATE_TIME_FORMAT_PATTERN;

@Getter
@Setter
public class UserRespDto {
    private UUID id;
    private String token;
    private Boolean isActive;
    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    @JsonFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime created;
    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    @JsonFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime modified;
    @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    @JsonFormat(pattern = LOCAL_DATE_TIME_FORMAT_PATTERN)
    private LocalDateTime lastLogin;

}

package com.previred.task_manager.components.advicer;


import com.previred.task_manager.components.exceptions.DuplicateDataException;
import com.previred.task_manager.components.exceptions.GeneralException;
import com.previred.task_manager.dto.base.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicateDataException ex) {
        log.error("GlobalExceptionHandler.handleDuplicateException: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        log.error("GlobalExceptionHandler.handleGeneralException: {}", ex.getMessage());

        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {
        log.error("GlobalExceptionHandler.handleMethodArgumentTypeMismatch: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("GlobalExceptionHandler.handleMethodArgumentNotValid: {}", ex.getMessage());
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.append(error.getField())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("; "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(String.valueOf(errors)));
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException ex) {
        log.error("GlobalExceptionHandler.handleGeneralException: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatusCode())
                .body(new ErrorResponseDto(String.valueOf(ex.getMessage())));
    }

}

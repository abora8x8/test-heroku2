package com.x8.tokengenerator.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class ExceptionManager {

    @ExceptionHandler(DependencyException.class)
    public ResponseEntity<Object> handleDependencyException(HttpServletRequest request, DependencyException ex) {
        log.error("", ex);
        return ResponseEntity.status(ex.getStatus())
                .body(ErrorBody.builder()
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(new Date().getTime())
                        .status(ex.getStatus().getReasonPhrase())
                        .build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        log.error("", ex);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorBody.builder()
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(new Date().getTime())
                        .status(INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build());
    }
}

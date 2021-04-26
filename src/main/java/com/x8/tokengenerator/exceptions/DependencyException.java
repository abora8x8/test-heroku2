package com.x8.tokengenerator.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependencyException extends RuntimeException {
    private HttpStatus status = BAD_REQUEST;
    private String message;

    public DependencyException(String message) {
        this.message = message;
    }
}

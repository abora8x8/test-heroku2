package com.x8.tokengenerator.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class ErrorBody implements Serializable {
    private long timestamp;
    private String status;
    private String message;
    private String path;
}

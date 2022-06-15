package com.myfreezer.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Data
public class ApiError {

    private String timestamp;
    private HttpStatus status;
    private int error;
    private String message;
    private String path;

    public ApiError(Exception ex, HttpServletRequest request, HttpStatus httpStatus){
        this.timestamp = LocalDateTime.now().toString();
        this.error = httpStatus.value();
        this.status = httpStatus;
        this.message = ex.getMessage();
        this.path = request.getRequestURL().toString();
    }

    public ApiError(Exception ex, HttpStatus httpStatus) {
        this.timestamp = LocalDateTime.now().toString();
        this.error = httpStatus.value();
        this.status = httpStatus;
        this.message = ex.getMessage();
    }

}

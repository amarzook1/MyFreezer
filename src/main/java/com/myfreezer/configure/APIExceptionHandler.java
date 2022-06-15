package com.myfreezer.configure;

import com.myfreezer.exceptions.NoDataFoundException;
import com.myfreezer.models.ApiError;
import com.myfreezer.services.FreezerServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LogManager.getLogger(APIExceptionHandler.class);

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<ApiError> handleBadCredentialsException(Exception ex) {
        logger.debug(ex);
        ApiError apiError = new ApiError(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ApiError> handleNodataFoundException(NoDataFoundException ex, HttpServletRequest request) {
        logger.warn(ex.getMessage());
        ApiError apiError = new ApiError(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}

package com.myfreezer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Object Not Found")
public class NoDataFoundException extends Exception {

    private static final long serialVersionUID = -1644680035066926404L;

    public NoDataFoundException(String message){
        super(message);
    }

}

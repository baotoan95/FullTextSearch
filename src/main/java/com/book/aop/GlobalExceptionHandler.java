package com.book.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.book.exceptions.AuthenticationException;

@ControllerAdvice(basePackages = { "com.book" })
public class GlobalExceptionHandler {
	
    @ResponseBody
    @ExceptionHandler(value = { AuthenticationException.class })
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<?> authorizationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}


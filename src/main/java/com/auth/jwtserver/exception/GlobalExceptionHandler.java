package com.auth.jwtserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.jwtserver.utility.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> invalidTokenException() {
        return ResponseBuilder.build(HttpStatus.UNAUTHORIZED, "Invalid Token", "No message available", null);
    }
    
    @ExceptionHandler(IncorrectLoginCredentialsException.class)
    public ResponseEntity<Object> incorrectLoginCredentialsException() {
        return ResponseBuilder.build(HttpStatus.UNAUTHORIZED, "Incorrect Username Or Password", "No message available", null);
    }
    
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> userAlreadyExistException() {
        return ResponseBuilder.build(HttpStatus.CONFLICT, "Username Is Already Taken", "No message available", null);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException() {
        return ResponseBuilder.build(HttpStatus.NOT_FOUND, "User Not Found", "No message available", null);
    }
    
}

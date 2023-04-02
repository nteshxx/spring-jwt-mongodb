package com.auth.jwtserver.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth.jwtserver.utility.ResponseBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseBuilder.build(HttpStatus.UNAUTHORIZED, "Invalid Token", ex.getMessage(), null);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    	Map<String, String> errorMessage = new HashMap<>();
    	// build error message
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errorMessage.put(fieldName, message);
		});
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, "Bad Request", errorMessage.toString(), null);
    }
    
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DuplicateKeyException ex) {
    	System.out.println(ex.getMostSpecificCause().getMessage());
    	if (ex.getMostSpecificCause().getMessage().contains("email dup key")) {
    		return ResponseBuilder.build(HttpStatus.CONFLICT, "Email Id Is Already Taken", ex.getMostSpecificCause().getMessage(), null);
    	} else if (ex.getMostSpecificCause().getMessage().contains("username dup key")) {
    		return ResponseBuilder.build(HttpStatus.CONFLICT, "Username Is Already Taken", ex.getMostSpecificCause().getMessage(), null);
    	}
        return ResponseBuilder.build(HttpStatus.CONFLICT, "Constraints Violated", ex.getMostSpecificCause().getMessage(), null);
    }
    
    @ExceptionHandler(IncorrectLoginCredentialsException.class)
    public ResponseEntity<Object> handleIncorrectLoginCredentialsException(IncorrectLoginCredentialsException ex) {
        return ResponseBuilder.build(HttpStatus.UNAUTHORIZED, "Incorrect Username Or Password", ex.getMessage(), null);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseBuilder.build(HttpStatus.NOT_FOUND, "Account Doesn't Exist", ex.getMessage(), null);
    }
    
}

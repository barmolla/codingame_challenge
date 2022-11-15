package com.codingame.challenge.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		 return new ResponseEntity<ErrorDetails>(new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
	}

    @ExceptionHandler(ResourceAlreadyExistsException.class)
	public final ResponseEntity<ErrorDetails> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false)), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleRuntimeException(RuntimeException ex) {
	    return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

package com.onito.globalexception;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionClass extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
@ExceptionHandler(MessagingException.class)
public ResponseEntity<?> handleMessagingException(MessagingException e){
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
}
@ExceptionHandler(IOException.class)
public ResponseEntity<?> handleIOException(IOException e){
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
}
	
}

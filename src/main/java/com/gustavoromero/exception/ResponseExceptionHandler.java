package com.gustavoromero.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	// 3 formas de manejo
	
		@ExceptionHandler(Exception.class)
		public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(ModeloNotFoundException ex, WebRequest request) {
			ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
			return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		@ExceptionHandler(ModeloNotFoundException.class)
		public final ResponseEntity<ExceptionResponse> manejarModeloException(ModeloNotFoundException ex, WebRequest request) {
			ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false)); 
			return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
		}
		
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
			return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
		}

}

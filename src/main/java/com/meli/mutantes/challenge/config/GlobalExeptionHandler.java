package com.meli.mutantes.challenge.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.meli.mutantes.challenge.common.ApiException;
import com.meli.mutantes.challenge.common.ExceptionResponse;

@ControllerAdvice
public class GlobalExeptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ExceptionResponse> apiException(ApiException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setError(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionResponse> runtimeException(RuntimeException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setError("No fue posible tramitar la petición");
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> internalServerError(Exception ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setError("No fue posible tramitar la petición");
		response.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

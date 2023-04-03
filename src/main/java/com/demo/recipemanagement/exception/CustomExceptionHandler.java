package com.demo.recipemanagement.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(RecipeNotFoundException ex, WebRequest request) {

	Map<String, Object> responseBody = new LinkedHashMap<>();
	responseBody.put("status", HttpStatus.NOT_FOUND.value());
	responseBody.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
	responseBody.put("message", ex.getMessage());

	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
	    HttpStatus status, WebRequest request) {

	Map<String, Object> responseBody = new LinkedHashMap<>();
	responseBody.put("status", status.value());
	responseBody.put("error", status.getReasonPhrase());
	responseBody.put("message", ex.getMessage());
	return ResponseEntity.status(status).body(responseBody);
    }
}
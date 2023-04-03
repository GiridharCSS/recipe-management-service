package com.demo.recipemanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class CustomExceptionHandlerTest {

    private CustomExceptionHandler exceptionHandler;
    private WebRequest request;

    @BeforeEach
    void setUp() {
	exceptionHandler = new CustomExceptionHandler();
	request = new ServletWebRequest(new MockHttpServletRequest(), new MockHttpServletResponse());
    }

    @SuppressWarnings("unchecked")
    @Test
    void handleRecipeNotFoundException_shouldReturnNotFoundResponse() {
	RecipeNotFoundException exception = new RecipeNotFoundException("Recipe not found");

	ResponseEntity<Object> responseEntity = exceptionHandler.handleRecipeNotFoundException(exception, request);

	assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
	assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.get("status"));
	assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), responseBody.get("error"));
	assertEquals("Recipe not found", responseBody.get("message"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void handleExceptionInternal_shouldReturnErrorResponse() {
	Exception exception = new Exception("Some error occurred");

	ResponseEntity<Object> responseEntity = exceptionHandler.handleExceptionInternal(exception, null,
		new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
	Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("status"));
	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), responseBody.get("error"));
	assertEquals("Some error occurred", responseBody.get("message"));
    }

}
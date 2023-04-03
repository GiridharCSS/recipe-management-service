package com.demo.recipemanagement.exception;

public class RecipeNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(String message) {
	super(message);
    }
}
package com.demo.recipemanagement.dto;

import java.util.List;

/**
 * Dto representing a recipe
 */
public class RecipeDto {

    private Long id;

    private String recipeName;

    private String creationDateTime;

    private Boolean isVegetarian;

    private Integer serves;

    private List<IngredientDto> ingredients;

    private String cookingInstructions;

    public RecipeDto() {
	super();
    }

    public RecipeDto(Long id, String recipeName, String creationDateTime, Boolean isVegetarian, Integer serves,
	    List<IngredientDto> ingredients, String cookingInstructions) {
	super();
	this.id = id;
	this.recipeName = recipeName;
	this.creationDateTime = creationDateTime;
	this.isVegetarian = isVegetarian;
	this.serves = serves;
	this.ingredients = ingredients;
	this.cookingInstructions = cookingInstructions;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getRecipeName() {
	return recipeName;
    }

    public void setRecipeName(String recipeName) {
	this.recipeName = recipeName;
    }

    public String getCreationDateTime() {
	return creationDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
	this.creationDateTime = creationDateTime;
    }

    public Boolean getIsVegetarian() {
	return isVegetarian;
    }

    public void setIsVegetarian(Boolean isVegetarian) {
	this.isVegetarian = isVegetarian;
    }

    public Integer getServes() {
	return serves;
    }

    public void setServes(Integer serves) {
	this.serves = serves;
    }

    public List<IngredientDto> getIngredients() {
	return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
	this.ingredients = ingredients;
    }

    public String getCookingInstructions() {
	return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
	this.cookingInstructions = cookingInstructions;
    }

}
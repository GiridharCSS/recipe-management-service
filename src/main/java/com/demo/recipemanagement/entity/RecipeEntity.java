package com.demo.recipemanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity representing a recipe
 */
@Entity
@Table(name = "recipe")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_name")
    private String recipeName;

    private String creationDateTime;

    private Boolean isVegetarian;

    private Integer serves;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<IngredientEntity> ingredients;

    @Column(columnDefinition = "TEXT")
    private String cookingInstructions;

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

    public List<IngredientEntity> getIngredients() {
	return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
	this.ingredients = ingredients;
    }

    public String getCookingInstructions() {
	return cookingInstructions;
    }

    public void setCookingInstructions(String cookingInstructions) {
	this.cookingInstructions = cookingInstructions;
    }

}
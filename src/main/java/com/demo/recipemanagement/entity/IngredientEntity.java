package com.demo.recipemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo.recipemanagement.entity.enums.MeasurementUnit;

/**
 * Entity representing an ingredient
 */
@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ingredient_name")
    private String ingredientName;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_unit")
    private MeasurementUnit measurementUnit;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "recipe_id")
//    private RecipeEntity recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getIngredientName() {
	return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
	this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
	return quantity;
    }

    public void setQuantity(Double quantity) {
	this.quantity = quantity;
    }

    public MeasurementUnit getMeasurementUnit() {
	return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
	this.measurementUnit = measurementUnit;
    }

    public RecipeEntity getRecipe() {
	return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
	this.recipe = recipe;
    }

}
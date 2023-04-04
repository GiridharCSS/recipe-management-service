package com.demo.recipemanagement.dto;

import com.demo.recipemanagement.entity.enums.MeasurementUnit;

/**
 * Dto representing an ingredient
 */
public class IngredientDto {

    private Long id;

    private String ingredientName;

    private Double quantity;

    private MeasurementUnit measurementUnit;

    public IngredientDto() {
	super();
    }

    public IngredientDto(Long id, String ingredientName, Double quantity, MeasurementUnit measurementUnit) {
	super();
	this.id = id;
	this.ingredientName = ingredientName;
	this.quantity = quantity;
	this.measurementUnit = measurementUnit;
    }

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

}
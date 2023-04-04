package com.demo.recipemanagement.service;

import java.util.List;

import com.demo.recipemanagement.dto.RecipeDto;

public interface RecipeManagementService {

    RecipeDto createRecipe(RecipeDto recipeDto);

    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);

    void deleteRecipe(Long id);

    RecipeDto getRecipeById(Long id);

    List<RecipeDto> getAllRecipes();
}
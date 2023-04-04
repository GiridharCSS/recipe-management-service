package com.demo.recipemanagement.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.demo.recipemanagement.dto.RecipeDto;
import com.demo.recipemanagement.service.RecipeManagementService;

class RecipeControllerTest {

    private RecipeManagementService recipeService;
    private RecipeManagementController recipeController;

    @BeforeEach
    public void setUp() {
	recipeService = Mockito.mock(RecipeManagementService.class);
	recipeController = new RecipeManagementController(recipeService);
    }

    @Test
    void testGetAllRecipes() {

	RecipeDto recipe1 = new RecipeDto();
	RecipeDto recipe2 = new RecipeDto();
	List<RecipeDto> recipes = Arrays.asList(recipe1, recipe2);
	Mockito.when(recipeService.getAllRecipes()).thenReturn(recipes);
	ResponseEntity<List<RecipeDto>> result = recipeController.getAllRecipes();
	assertThat(result.getBody()).isEqualTo(recipes);
    }

    @Test
    void testGetRecipeById() {

	RecipeDto recipe = new RecipeDto();
	recipe.setId(1L);
	Mockito.when(recipeService.getRecipeById(1L)).thenReturn(recipe);
	ResponseEntity<RecipeDto> result = recipeController.getRecipeById(1L);
	assertThat(result.getBody()).isEqualTo(recipe);
    }

    @Test
    void testCreateRecipe() {

	RecipeDto recipe = new RecipeDto();
	recipe.setId(1L);
	Mockito.when(recipeService.createRecipe(recipe)).thenReturn(recipe);
	ResponseEntity<RecipeDto> result = recipeController.createRecipe(recipe);
	assertThat(result.getBody()).isEqualTo(recipe);
    }

    @Test
    void testUpdateRecipe() {

	RecipeDto recipe = new RecipeDto();
	recipe.setId(1L);
	Mockito.when(recipeService.updateRecipe(1L, recipe)).thenReturn(recipe);
	ResponseEntity<RecipeDto> result = recipeController.updateRecipe(1L, recipe);
	assertThat(result.getBody()).isEqualTo(recipe);
    }

    @Test
    void testDeleteRecipe() {

	recipeController.deleteRecipe(1L);
	Mockito.verify(recipeService, Mockito.times(1)).deleteRecipe(1L);
    }
}
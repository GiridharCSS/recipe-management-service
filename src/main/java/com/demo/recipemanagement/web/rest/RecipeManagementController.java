package com.demo.recipemanagement.web.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.recipemanagement.dto.RecipeDto;
import com.demo.recipemanagement.service.RecipeManagementService;

@RestController
@RequestMapping("recipes")
public class RecipeManagementController {

    private final RecipeManagementService recipeManagementService;

    public RecipeManagementController(RecipeManagementService recipeManagementService) {
	this.recipeManagementService = recipeManagementService;
    }

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {

	return ResponseEntity.ok().body(recipeManagementService.getAllRecipes());
    }

    @GetMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable(required = true) Long id) {

	return ResponseEntity.ok().body(recipeManagementService.getRecipeById(id));
    }

    @PostMapping(produces = { "application/json" })
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {

	return ResponseEntity.ok().body(recipeManagementService.createRecipe(recipeDto));
    }

    @PutMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {

	return ResponseEntity.ok(recipeManagementService.updateRecipe(id, recipeDto));
    }

    @DeleteMapping(value = "/{id}", produces = { "application/json" })
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {

	recipeManagementService.deleteRecipe(id);

	String responseMessage = "Deleted the recipe with id " + id + " successfully.";

	return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }

}
package com.demo.recipemanagement.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.demo.recipemanagement.dto.RecipeDto;
import com.demo.recipemanagement.entity.IngredientEntity;
import com.demo.recipemanagement.entity.RecipeEntity;
import com.demo.recipemanagement.exception.RecipeNotFoundException;
import com.demo.recipemanagement.repository.RecipeManagementRepository;
import com.demo.recipemanagement.service.RecipeManagementService;

@Service
public class RecipeManagementServiceImpl implements RecipeManagementService {

    private final RecipeManagementRepository recipeManagementRepository;
    private final ModelMapper modelMapper;

    private static final String NOT_FOUND_ERROR_MESSAGE = "Recipe with id %d is not found";

    public RecipeManagementServiceImpl(RecipeManagementRepository recipeManagementRepository, ModelMapper modelMapper) {

	this.recipeManagementRepository = recipeManagementRepository;
	this.modelMapper = modelMapper;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {

	RecipeEntity recipe = new RecipeEntity();

	recipe.setRecipeName(recipeDto.getRecipeName());
	recipe.setCreationDateTime(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(LocalDateTime.now()));
	recipe.setIsVegetarian(recipeDto.getIsVegetarian());
	recipe.setServes(recipeDto.getServes());
	recipe.setIngredients(recipeDto.getIngredients().stream()
		.map(ingredientDto -> modelMapper.map(ingredientDto, IngredientEntity.class))
		.collect(Collectors.toList()));
	recipe.setCookingInstructions(recipeDto.getCookingInstructions());
	final RecipeEntity recipeFinal = recipe;
	recipe.getIngredients().forEach(ingredientEntity -> ingredientEntity.setRecipe(recipeFinal));
	recipe = recipeManagementRepository.save(recipe);

	return modelMapper.map(recipe, RecipeDto.class);
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {

	RecipeEntity recipe = recipeManagementRepository.findById(id)
		.orElseThrow(() -> new RecipeNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, id)));

	// update the recipe entity with the new data
	recipe.setRecipeName(recipeDto.getRecipeName());
	recipe.setCreationDateTime(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(LocalDateTime.now()));
	recipe.setIsVegetarian(recipeDto.getIsVegetarian());
	recipe.setServes(recipeDto.getServes());
	recipe.setIngredients(recipeDto.getIngredients().stream()
		.map(ingredientDto -> modelMapper.map(ingredientDto, IngredientEntity.class))
		.collect(Collectors.toList()));
	recipe.setCookingInstructions(recipeDto.getCookingInstructions());
	final RecipeEntity recipeFinal = recipe;
	recipe.getIngredients().forEach(ingredientEntity -> ingredientEntity.setRecipe(recipeFinal));
	recipe = recipeManagementRepository.save(recipe);

	return modelMapper.map(recipe, RecipeDto.class);
    }

    @Override
    public void deleteRecipe(Long id) {

	RecipeEntity recipe = recipeManagementRepository.findById(id)
		.orElseThrow(() -> new RecipeNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, id)));
	recipeManagementRepository.delete(recipe);
    }

    @Override
    public RecipeDto getRecipeById(Long id) {

	Optional<RecipeEntity> recipe = recipeManagementRepository.findById(id);

	if (recipe.isEmpty()) {
	    throw new RecipeNotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE, id));
	}

	return modelMapper.map(recipe.get(), RecipeDto.class);
    }

    @Override
    public List<RecipeDto> getAllRecipes() {

	List<RecipeEntity> recipes = recipeManagementRepository.findAll();

	return recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeDto.class)).collect(Collectors.toList());
    }

}
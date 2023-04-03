package com.demo.recipemanagement.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import com.demo.recipemanagement.dto.IngredientDto;
import com.demo.recipemanagement.dto.RecipeDto;
import com.demo.recipemanagement.entity.IngredientEntity;
import com.demo.recipemanagement.entity.RecipeEntity;
import com.demo.recipemanagement.entity.enums.MeasurementUnit;
import com.demo.recipemanagement.exception.RecipeNotFoundException;
import com.demo.recipemanagement.repository.RecipeManagementRepository;

class RecipeManagementServiceImplTest {

    @Mock
    private RecipeManagementRepository recipeManagementRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RecipeManagementServiceImpl recipeManagementService;

    private RecipeEntity recipeEntity;

    private RecipeDto recipeDto;

    private RecipeDto recipeDto_2;

    private IngredientEntity ingredientEntity;

    private IngredientDto ingredientDto;

    private IngredientDto ingredientDto_2;

    Long recipeId = 1L;

    @BeforeEach
    public void setup() {

	recipeManagementRepository = Mockito.mock(RecipeManagementRepository.class);
	modelMapper = Mockito.mock(ModelMapper.class);
	recipeManagementService = new RecipeManagementServiceImpl(recipeManagementRepository, modelMapper);

	ingredientDto = new IngredientDto();
	ingredientDto.setId(1L);
	ingredientDto.setQuantity(5.0);
	ingredientDto.setIngredientName("Salt");
	ingredientDto.setMeasurementUnit(MeasurementUnit.GRAM);

	ingredientDto_2 = new IngredientDto(2L, "Pepper", 5.0, MeasurementUnit.GRAM);

	ingredientEntity = new IngredientEntity();
	ingredientEntity.setId(1L);
	ingredientEntity.setQuantity(5.0);
	ingredientEntity.setIngredientName("Salt");
	ingredientEntity.setMeasurementUnit(MeasurementUnit.GRAM);

	List<IngredientDto> ingredientDtos = new ArrayList<>();
	ingredientDtos.add(ingredientDto);

	List<IngredientEntity> ingredientEntities = new ArrayList<>();
	ingredientEntities.add(ingredientEntity);

	recipeDto = new RecipeDto();
	recipeDto.setId(1L);
	recipeDto.setRecipeName("Burger");
	recipeDto.setServes(2);
	recipeDto.setIngredients(ingredientDtos);
	recipeDto.setCookingInstructions("test");
	recipeDto.setCreationDateTime((DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(LocalDateTime.now())));
	recipeDto.setIsVegetarian(true);

	recipeDto_2 = new RecipeDto(2L, "Pizza", LocalDate.now().toString(), true, 2, ingredientDtos, "test");

	recipeEntity = new RecipeEntity();
	recipeEntity.setId(1L);
	recipeEntity.setRecipeName("Burger");
	recipeEntity.setServes(2);
	ingredientEntities.get(0).setRecipe(recipeEntity);
	recipeEntity.setIngredients(ingredientEntities);
	recipeEntity.setCookingInstructions("test");
	recipeEntity.setCreationDateTime((DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(LocalDateTime.now())));
	recipeEntity.setIsVegetarian(true);
    }

    @Test
    void testCreateRecipe() {

	Mockito.when(recipeManagementRepository.save(ArgumentMatchers.any(RecipeEntity.class)))
		.thenReturn(recipeEntity);
	Mockito.when(modelMapper.map(ArgumentMatchers.any(RecipeEntity.class), ArgumentMatchers.eq(RecipeDto.class)))
		.thenReturn(recipeDto);
	Mockito.when(
		modelMapper.map(ArgumentMatchers.any(IngredientDto.class), ArgumentMatchers.eq(IngredientEntity.class)))
		.thenReturn(ingredientEntity);
	RecipeDto createdRecipe = recipeManagementService.createRecipe(recipeDto);
	assertThat(createdRecipe.getId()).isEqualTo(1L);
	assertThat(createdRecipe).isNotNull();
	assertThat(createdRecipe.getRecipeName()).isEqualTo("Burger");
	assertNotNull(createdRecipe.getCreationDateTime());
	assertThat(createdRecipe.getServes()).isEqualTo(2);
	assertThat(createdRecipe.getIngredients()).hasSize(1);
	assertThat(createdRecipe.getIngredients().get(0).getId()).isEqualTo(1L);
	assertThat(createdRecipe.getIngredients().get(0).getQuantity()).isEqualTo(5.0);
	assertThat(createdRecipe.getIngredients().get(0).getIngredientName()).isEqualTo("Salt");
	assertThat(createdRecipe.getIngredients().get(0).getMeasurementUnit()).isEqualTo(MeasurementUnit.GRAM);
    }

    @Test
    void testUpdateRecipe_When_Success() {

	Mockito.when(recipeManagementRepository.findById(ArgumentMatchers.any(Long.class)))
		.thenReturn(Optional.of(recipeEntity));
	Mockito.when(recipeManagementRepository.save(ArgumentMatchers.any(RecipeEntity.class)))
		.thenReturn(recipeEntity);
	Mockito.when(modelMapper.map(ArgumentMatchers.any(RecipeEntity.class), ArgumentMatchers.eq(RecipeDto.class)))
		.thenReturn(recipeDto);
	Mockito.when(
		modelMapper.map(ArgumentMatchers.any(IngredientDto.class), ArgumentMatchers.eq(IngredientEntity.class)))
		.thenReturn(ingredientEntity);
	RecipeDto updatedRecipe = recipeManagementService.updateRecipe(1L, recipeDto);
	assertThat(updatedRecipe).isNotNull();
	assertThat(updatedRecipe.getRecipeName()).isEqualTo("Burger");
	assertThat(updatedRecipe.getServes()).isEqualTo(2);
	assertThat(updatedRecipe.getIngredients()).hasSize(1);
    }

    @Test
    void testUpdateRecipe_When_Failure() {

	assertThrows(RecipeNotFoundException.class, () -> recipeManagementService.updateRecipe(recipeId, recipeDto));
	Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(), Mockito.eq(RecipeDto.class));

    }

    @Test
    void testDeleteRecipe_When_Success() {

	RecipeEntity recipe = new RecipeEntity();
	recipe.setId(recipeId);
	Mockito.when(recipeManagementRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
	recipeManagementService.deleteRecipe(recipeId);
	Mockito.verify(recipeManagementRepository).delete(recipe);
    }

    @Test
    void testDeleteRecipe_When_Failure() {

	Mockito.when(recipeManagementRepository.findById(recipeId)).thenReturn(Optional.empty());
	assertThrows(RecipeNotFoundException.class, () -> recipeManagementService.deleteRecipe(recipeId));
	Mockito.verify(recipeManagementRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    void testGetRecipeById_When_Success() {

	RecipeEntity recipe = new RecipeEntity();
	recipe.setId(recipeId);
	RecipeDto expectedRecipeDto = new RecipeDto();
	expectedRecipeDto.setId(recipeId);
	Mockito.when(recipeManagementRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
	Mockito.when(modelMapper.map(recipe, RecipeDto.class)).thenReturn(expectedRecipeDto);
	RecipeDto actualRecipeDto = recipeManagementService.getRecipeById(recipeId);
	assertEquals(expectedRecipeDto, actualRecipeDto);
    }

    @Test
    void testGetRecipeById_When_Failure() {

	Mockito.when(recipeManagementRepository.findById(recipeId)).thenReturn(Optional.empty());
	assertThrows(RecipeNotFoundException.class, () -> recipeManagementService.getRecipeById(recipeId));
	Mockito.verify(modelMapper, Mockito.never()).map(Mockito.any(), Mockito.eq(RecipeDto.class));
    }

    @Test
    void testGetAllRecipes() {

	List<RecipeEntity> recipeEntities = Arrays.asList(new RecipeEntity(), new RecipeEntity());
	List<RecipeDto> expectedRecipeDtos = Arrays.asList(new RecipeDto(), new RecipeDto());
	Mockito.when(recipeManagementRepository.findAll()).thenReturn(recipeEntities);
	Mockito.when(modelMapper.map(Mockito.any(RecipeEntity.class), Mockito.eq(RecipeDto.class)))
		.thenReturn(new RecipeDto()).thenReturn(new RecipeDto());
	List<RecipeDto> actualRecipeDtos = recipeManagementService.getAllRecipes();
	assertEquals(expectedRecipeDtos.size(), actualRecipeDtos.size());
    }
}
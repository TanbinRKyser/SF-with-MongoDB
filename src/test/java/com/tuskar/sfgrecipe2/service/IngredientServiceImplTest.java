package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;
import com.tuskar.sfgrecipe2.converters.IngredientCommandToIngredient;
import com.tuskar.sfgrecipe2.converters.IngredientToIngredientCommand;
import com.tuskar.sfgrecipe2.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.tuskar.sfgrecipe2.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.tuskar.sfgrecipe2.domain.Ingredient;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import com.tuskar.sfgrecipe2.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */
public class IngredientServiceImplTest {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("2");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findRecipeIdAndIngredientId("1","3");

        assertEquals("3",ingredientCommand.getId());
        assertEquals("1",ingredientCommand.getRecipeId());
        verify(recipeRepository,times(1)).findById(anyString());

    }

    @Test
    public void testSaveOrUpdateRecipeCommand() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("3");
        ingredientCommand.setRecipeId("2");

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals("3",savedCommand.getId());
        verify(recipeRepository,times(1)).findById(anyString());
        verify(recipeRepository,times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteIngredientById() {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");

        recipe.addIngredient(ingredient);
//        ingredient.setRecipe(recipe);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);

        ingredientService.deleteIngredientById("1","3");

        verify(recipeRepository,times(1)).findById( anyString() );
        verify(recipeRepository, times(1)).save( any(Recipe.class) );
    }
}
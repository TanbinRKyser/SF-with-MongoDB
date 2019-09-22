package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.converters.RecipeCommandToRecipe;
import com.tuskar.sfgrecipe2.converters.RecipeToRecipeCommand;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.exceptions.NotFoundException;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 9/2/2019
 */
public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    private RecipeToRecipeCommand recipeToRecipeCommand;
    private RecipeCommandToRecipe recipeCommandToRecipe;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );

        recipeService = new RecipeServiceImpl( recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId( "1" );
        Optional<Recipe> recipeOptional = Optional.of( recipe );

        when( recipeRepository.findById( ArgumentMatchers.anyString() )).thenReturn( recipeOptional );

        Recipe recipeReturned = recipeService.findById("1");

        assertNotNull("Null Recipe Returned",recipeReturned);
        verify( recipeRepository, times( 1 )).findById( ArgumentMatchers.anyString() );
        verify( recipeRepository,never() ).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() throws Exception{
        Optional<Recipe> optionalRecipe = Optional.empty();

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);

        Recipe returnedRecipe = recipeService.findById("1");
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when( recipeService.getRecipes()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(),recipeData.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyString());
    }

    @Test
    public void testDeleteById() {
//        Long idToDelete = 2L;
        String idToDelete = "2";
        recipeService.deleteById(idToDelete);

        verify(recipeRepository,times(1)).deleteById(anyString());

    }
}
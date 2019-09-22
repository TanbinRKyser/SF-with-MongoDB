package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.converters.RecipeCommandToRecipe;
import com.tuskar.sfgrecipe2.converters.RecipeToRecipeCommand;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */

@Ignore
@DataMongoTest
@RunWith(SpringRunner.class)
//@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION="New Description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

//    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception{
        // Arrange || Given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // Act || When
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //Assert || Then
        assertEquals(NEW_DESCRIPTION,savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(),savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(),savedRecipeCommand.getIngredients().size());
    }
}
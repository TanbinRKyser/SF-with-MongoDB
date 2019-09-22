package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */
public interface IngredientService {
    IngredientCommand findRecipeIdAndIngredientId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredientById(String recipeId, String ingredientId);
}

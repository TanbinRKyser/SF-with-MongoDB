package com.tuskar.sfgrecipe2.converters;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;
import com.tuskar.sfgrecipe2.domain.Ingredient;
import com.tuskar.sfgrecipe2.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 04-Sep-19
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();

        ingredient.setId(ingredientCommand.getId());

        if(ingredientCommand.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
//            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUom(uomConverter.convert(ingredientCommand.getUom()));

        return ingredient;
    }
}

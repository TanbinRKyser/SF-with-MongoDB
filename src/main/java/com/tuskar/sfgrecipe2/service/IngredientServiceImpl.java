package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;
import com.tuskar.sfgrecipe2.converters.IngredientCommandToIngredient;
import com.tuskar.sfgrecipe2.converters.IngredientToIngredientCommand;
import com.tuskar.sfgrecipe2.domain.Ingredient;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import com.tuskar.sfgrecipe2.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Transactional
    @Override
    public IngredientCommand findRecipeIdAndIngredientId(String recipeId, String id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            log.error("Ingredient id not found: " + id);
        }

        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipe.getId());

        return ingredientCommandOptional.get();
    }

//    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()){
            log.error("recipe id not found. Id: " + ingredientCommand.getRecipeId());

            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                                                            .stream()
                                                            .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                                                            .findFirst();
            if (ingredientOptional.isPresent() ){

                Ingredient ingredientFound = ingredientOptional.get();

                ingredientFound.setDescription( ingredientCommand.getDescription() );
                ingredientFound.setAmount( ingredientCommand.getAmount() );
                ingredientFound.setUom( unitOfMeasureRepository
                                        .findById( ingredientCommand.getUom().getId() )
                                        .orElseThrow( () -> new RuntimeException( "UoM not found" ) ) );
            } else {
                // add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
//                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                                                                        .filter( recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                                                                        .findFirst();

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients()
                                                    .stream()
                                                    .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                                                    .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                                                    .filter(recipeIngredient->recipeIngredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                                                    .findFirst();
            }
//            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());

            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return ingredientCommandSaved;
        }

    }

    @Override
    public void deleteIngredientById(String recipeId, String ingredientId) {

        log.debug("Deleting Ingredient. Recipe id: " + recipeId + " Ingredient Id: " +ingredientId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            log.debug("Found Recipe. Id : " + recipeId);

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                                                            .stream()
                                                            .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                                            .findFirst();
            if( ingredientOptional.isPresent() ){
                log.debug("Found Ingredient. Id: " + ingredientId);

                Ingredient ingredientToDelete = ingredientOptional.get();
//                ingredientToDelete.setRecipe(null);

                recipe.getIngredients().remove(ingredientOptional.get());

                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Ingredient not found in Recipe. Recipe Id: " + recipeId);
        }

    }
}

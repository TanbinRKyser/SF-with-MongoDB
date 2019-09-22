package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.converters.RecipeCommandToRecipe;
import com.tuskar.sfgrecipe2.converters.RecipeToRecipeCommand;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.exceptions.NotFoundException;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Transactional
    @Override
    public Set<Recipe> getRecipes() {

        log.debug("I'm in the service.");

        Set<Recipe> recipeSet = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining( recipeSet::add );

        return recipeSet;
    }

    @Override
    @Transactional
    public Recipe findById(String id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
//        if ( !optionalRecipe.isPresent()) throw new RuntimeException("Recipe Not Found");
        if ( !optionalRecipe.isPresent() ) throw new NotFoundException("Recipe Not Found. For ID value: " + id.toString() );

        return optionalRecipe.get();

    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug( "Saved Recipe Id: " + savedRecipe.getId() );

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
//        return recipeToRecipeCommand.convert(findById(id));

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id));
        if( recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0 ){
            recipeCommand.getIngredients().forEach( rc -> { rc.setRecipeId( recipeCommand.getId() ); } );
        }

        return recipeCommand;
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

}

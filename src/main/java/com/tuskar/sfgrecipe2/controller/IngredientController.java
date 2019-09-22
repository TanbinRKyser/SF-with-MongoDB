package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;
import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.commands.UnitOfMeasureCommand;
import com.tuskar.sfgrecipe2.service.IngredientService;
import com.tuskar.sfgrecipe2.service.RecipeService;
import com.tuskar.sfgrecipe2.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */
@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String getIngredientByList(@PathVariable String id, Model model){
        log.debug( "Getting ingredient list for recipe id: " + id );

        model.addAttribute("recipe",recipeService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model ){

        model.addAttribute("ingredient", ingredientService.findRecipeIdAndIngredientId(recipeId,id));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){

        RecipeCommand recipeCommand = recipeService.findCommandById(  recipeId );
        // todo : raise an exception if it is null.

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId( recipeId );
        model.addAttribute( "ingredient",ingredientCommand );

        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute( "uomList", unitOfMeasureService.listAllUoms() );

        return "recipe/ingredient/ingredientform";
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeString(@PathVariable String recipeId, @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findRecipeIdAndIngredientId(recipeId,id));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("Saved Recipe ID: " + savedCommand.getRecipeId());
        log.debug("Saved Ingredient ID: " + savedCommand.getId());

        return "redirect:/recipe/"+ savedCommand.getRecipeId() +"/ingredient/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){

        ingredientService.deleteIngredientById(recipeId,id);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}

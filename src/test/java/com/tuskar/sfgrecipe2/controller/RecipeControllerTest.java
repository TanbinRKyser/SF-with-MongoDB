package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.exceptions.NotFoundException;
import com.tuskar.sfgrecipe2.service.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 9/4/2019
 */
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController( recipeService );
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                                .setControllerAdvice(ControllerExceptionHandler.class)      // Added for Global Exception Handler.
                                .build();
    }

    @Test
    public void testGetRecipe() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when( recipeService.findById(anyString() )).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception{

        when( recipeService.findById( anyString() )).thenThrow( NotFoundException.class );

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Ignore
    @Test
    public void testHandleNumberFormatException() throws Exception {
        /*Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.findById(anyLong())).thenReturn(recipe);*/

//        when(recipeService.findById(anyLong())).thenThrow(NumberFormatException.class);

        mockMvc.perform(get("/recipe/jlk/show"))
                .andExpect( status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId( "2" );

        when( recipeService.saveRecipeCommand( any() ) ).thenReturn( recipeCommand );

        mockMvc.perform( post("/recipe")
                        .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                        .param("id","")
                        .param("description","some string")
                        .param("directions", "some directions"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        when( recipeService.saveRecipeCommand( any() )).thenReturn( recipeCommand );

        mockMvc.perform( post("/recipe")
                        .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                        .param("id","")
                        .param("cookTime","3000")
                )
                .andExpect( status().isOk() )
                .andExpect( model().attributeExists("recipe") )
                .andExpect( view().name("recipe/recipeform") );

    }

    @Test
    public void testGetUpdateView() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService,times(1)).deleteById(anyString());
    }
}
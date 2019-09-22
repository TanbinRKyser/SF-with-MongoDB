package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.commands.IngredientCommand;
import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.service.IngredientService;
import com.tuskar.sfgrecipe2.service.RecipeService;
import com.tuskar.sfgrecipe2.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 05-Sep-19
 */
public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    IngredientService ingredientService;
//    @InjectMocks
    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService,times(1)).findCommandById(anyString());
    }

    @Test
    public void testShowIngredients() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findRecipeIdAndIngredientId(anyString(),anyString())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testUpdateIngredientMethods() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findRecipeIdAndIngredientId(anyString(),anyString())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId( "2" );
        ingredientCommand.setId( "3" );

        when( ingredientService.saveIngredientCommand( any() ) ).thenReturn( ingredientCommand );

        mockMvc.perform( post( "/recipe/2/ingredient" )
                        .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                        .param( "id","")
                        .param( "description","Some String")
                        )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name( "redirect:/recipe/2/ingredient/3/show" )
                );
    }

    @Test
    public void testDeleteIngredient() throws Exception {

        mockMvc.perform( get( "/recipe/2/ingredient/3/delete" ) )
                .andExpect( status().is3xxRedirection() )
                .andExpect( view().name("redirect:/recipe/2/ingredients") );

        verify( ingredientService,times( 1 ) ).deleteIngredientById( anyString(), anyString() );
    }
}
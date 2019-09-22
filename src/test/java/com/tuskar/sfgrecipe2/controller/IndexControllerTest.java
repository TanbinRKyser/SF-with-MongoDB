package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 9/2/2019
 */
public class IndexControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void getRecipes() {

        /* Given*/
        Set<Recipe> recipes = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setId("1");
        recipes.add(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setId("2");
        recipes.add(recipe2);

        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set> argumentCaptor = ArgumentCaptor.forClass(Set.class);

         /*When*/
        String viewName = indexController.getRecipes(model);


         /*Then*/
        assertEquals("index",viewName);
        Mockito.verify(recipeService,Mockito.times(1)).getRecipes();
//        Mockito.verify(model,Mockito.times(1)).addAttribute(Mockito.eq("recipes"),Mockito.anySet());
        Mockito.verify(model,Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

        Set<Recipe> recipeSet = argumentCaptor.getValue();
        assertEquals(2, recipeSet.size());

    }
}
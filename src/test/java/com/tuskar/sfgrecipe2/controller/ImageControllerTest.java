package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.service.ImageService;
import com.tuskar.sfgrecipe2.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 08-Sep-19
 */
public class ImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    private ImageController imageController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController( imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(ControllerExceptionHandler.class).build();
    }

    @Test
    public void testImageForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect( model().attributeExists("recipe") );

        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void testHandleImagePost() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("imageFile","testing.txt","text/plain",
                        "Spring Framework Guru".getBytes());

        this.mockMvc.perform( multipart("/recipe/1/image").file( mockMultipartFile ))
                .andExpect( status().is3xxRedirection() )
                .andExpect( header().string("Location","/recipe/1/show") );
    }

    @Test
    public void testRenderImageFromDb() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        String text = "Fake image text";
        Byte[] bytesBoxed = new Byte[ text.getBytes().length ];

        int i = 0;

        for( byte primaryBytes : text.getBytes() ){
            bytesBoxed[i++] = primaryBytes;
        }

        recipeCommand.setImage(bytesBoxed);

        when(recipeService.findCommandById(anyString())).thenReturn(recipeCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals( text.getBytes().length, responseBytes.length );

    }

    /*@Test
    public void testHandleNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/jlk/recipeimage"))
                .andExpect( status().isBadRequest())
                .andExpect(view().name("400error"));
    }*/

}
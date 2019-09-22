package com.tuskar.sfgrecipe2.controller;

import com.tuskar.sfgrecipe2.commands.RecipeCommand;
import com.tuskar.sfgrecipe2.service.ImageService;
import com.tuskar.sfgrecipe2.service.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 08-Sep-19
 */

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }


    @GetMapping("recipe/{id}/image")
    public String imageForm(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(id));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String  id, @RequestParam("imageFile") MultipartFile file ){

        imageService.saveImageFile( id , file );

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        if(recipeCommand.getImage() != null){

            byte[] byteArray = new byte[recipeCommand.getImage().length];

            int i=0;

            for(Byte wrapByte : recipeCommand.getImage()){
                byteArray[i++] = wrapByte;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream,response.getOutputStream());
        }

    }
}

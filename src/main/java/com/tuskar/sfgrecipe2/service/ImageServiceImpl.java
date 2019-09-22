package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.domain.Recipe;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 08-Sep-19
 */

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }


    @Override
    @Transactional
    public void saveImageFile(String id, MultipartFile file) {
        log.debug("Received a file");

        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for(byte b: file.getBytes() ){
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);

        } catch (IOException e) {
            // todo handle error
            log.error("Error occurred.", e);

            e.printStackTrace();
        }
    }
}

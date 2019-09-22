package com.tuskar.sfgrecipe2.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 08-Sep-19
 */
public interface ImageService {
    void saveImageFile(String id, MultipartFile file);
}

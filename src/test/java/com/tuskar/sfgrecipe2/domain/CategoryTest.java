package com.tuskar.sfgrecipe2.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 9/2/2019
 */


public class CategoryTest {

    private Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getId() {
//        Long idValue = 1l;
        String idValue = "1";

        category.setId(idValue);

        assertEquals( idValue, category.getId() );
    }

    @Test
    public void getDescription() {

    }

    @Test
    public void getRecipes() {
    }
}

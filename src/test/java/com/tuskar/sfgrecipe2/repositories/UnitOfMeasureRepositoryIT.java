package com.tuskar.sfgrecipe2.repositories;

import com.tuskar.sfgrecipe2.bootstrap.DataLoader;
import com.tuskar.sfgrecipe2.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 9/2/2019
 */

//@Ignore
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();

        DataLoader dataLoader = new DataLoader(recipeRepository,categoryRepository,unitOfMeasureRepository);

        dataLoader.onApplicationEvent(null);
    }

    @Test
    public void findByDescription() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals( "Teaspoon", unitOfMeasure.get().getDescription() );
    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals( "Cup", unitOfMeasure.get().getDescription() );
    }
}
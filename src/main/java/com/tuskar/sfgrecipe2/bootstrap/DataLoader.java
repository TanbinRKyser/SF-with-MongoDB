package com.tuskar.sfgrecipe2.bootstrap;

import com.tuskar.sfgrecipe2.domain.*;
import com.tuskar.sfgrecipe2.repositories.CategoryRepository;
import com.tuskar.sfgrecipe2.repositories.RecipeRepository;
import com.tuskar.sfgrecipe2.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
//@Profile("default")
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional // To avoid lazy initialization.
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

//        log.debug("Loading categories");

        loadCategories();
        loadUoMs();
//        log.debug("Loading UoMs");


        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data.");
    }

    private void loadUoMs() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setDescription("Each");
        unitOfMeasureRepository.save(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(unitOfMeasure2);

        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure3.setDescription("Teaspoon");
        unitOfMeasureRepository.save(unitOfMeasure3);

        UnitOfMeasure unitOfMeasure4 = new UnitOfMeasure();
        unitOfMeasure4.setDescription("Dash");
        unitOfMeasureRepository.save(unitOfMeasure4);

        UnitOfMeasure unitOfMeasure5 = new UnitOfMeasure();
        unitOfMeasure5.setDescription("Pint");
        unitOfMeasureRepository.save(unitOfMeasure5);

        UnitOfMeasure unitOfMeasure6 = new UnitOfMeasure();
        unitOfMeasure6.setDescription("Cup");
        unitOfMeasureRepository.save(unitOfMeasure6);

        UnitOfMeasure unitOfMeasure7 = new UnitOfMeasure();
        unitOfMeasure7.setDescription("Pinch");
        unitOfMeasureRepository.save(unitOfMeasure7);

        UnitOfMeasure unitOfMeasure8 = new UnitOfMeasure();
        unitOfMeasure8.setDescription("Ounce");
        unitOfMeasureRepository.save(unitOfMeasure8);

    }

    private void loadCategories() {
        Category category1 = new Category();
        category1.setDescription("American");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Mexican");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setDescription("Italian");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setDescription("Chinese");
        categoryRepository.save(category4);
    }

    private List<Recipe> getRecipes(){

        List<Recipe> recipes = new ArrayList<>(2);

        // Get UoMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }
        Optional<UnitOfMeasure> tablespoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!tablespoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }

        Optional<UnitOfMeasure> teaspoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!teaspoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if(!cupUomOptional.isPresent()){
            throw new RuntimeException("Expected UoM Not Found");
        }

        // get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tablespoonUomOptional.get();
        UnitOfMeasure teaspoonUom = teaspoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        // get categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
        if(!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> chineseCategoryOptional = categoryRepository.findByDescription("Chinese");
        if(!chineseCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category chineseCategory = chineseCategoryOptional.get();

        // Perfect Guacamole Recipe
        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon.\n"
                +"\n"
                +"2 Mash with a fork: Using a fork, roughly mash the avocado.\n"
                + "\n"
                +"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n"
                +"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n"
                +"\n"
                +"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
                +"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving."
                +"\n"
                +"\n"
                +"Read more: https://www.simplyrecipes.com/recipes/perfect_guacamole/"
        );

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
                +"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries\n"
                +"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n"
                +"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n"
                +"\n"
                +"\n"
                + "Read more: https://www.simplyrecipes.com/recipes/perfect_guacamole/"
        );

        // needed for bidirectional- should be one method call
//        guacamoleNotes.setRecipe(guacamoleRecipe);
        guacamoleRecipe.setNotes(guacamoleNotes);

        // redundant - could add a helper method and make this simpler.
        guacamoleRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Kosher salt",new BigDecimal(".5"), teaspoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice",new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed minceed", new BigDecimal(2), eachUom));
        guacamoleRecipe.addIngredient(new Ingredient("Cinatro", new BigDecimal(2), tableSpoonUom));
        guacamoleRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacamoleRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        guacamoleRecipe.getCategories().add(americanCategory);
        guacamoleRecipe.getCategories().add(mexicanCategory);

        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setSource("Simply Recipes");
        // add recipe to the list.
        recipes.add(guacamoleRecipe);


        // Spicy Grilled Chicken Recipe
        Recipe chickenTacosRecipe = new Recipe();
        chickenTacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        chickenTacosRecipe.setCookTime(9);
        chickenTacosRecipe.setPrepTime(20);
        chickenTacosRecipe.setDifficulty(Difficulty.MODERATE);

        chickenTacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n"
                +"\n"
                +"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n"
                +"Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n"
                +"\n"
                +"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n"
                +"\n"
                +"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n"
                +"Wrap warmed tortillas in a tea towel to keep them warm until serving.\n"
                +"\n"
                +"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n"
                +"\n"
                +"\n"
                +"Read more : https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/"
        );

        Notes chickenTacosNotes = new Notes();
        chickenTacosNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n"
                +"Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house."
                +"Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n"
                +"First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n"
                +"Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n"
                +"\n"
                +"\n"
                +"Read more : https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/"

        );

//        chickenTacosNotes.setRecipe(chickenTacosRecipe);
        chickenTacosRecipe.setNotes(chickenTacosNotes);

        chickenTacosRecipe.getIngredients().add( new Ingredient("ancho chili powder", new BigDecimal(2), tableSpoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("dried oregano", new BigDecimal(1), teaspoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("dried cumin", new BigDecimal(1), teaspoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("sugar", new BigDecimal(1), teaspoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("salt", new BigDecimal(".5"),teaspoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("clove garlic", new BigDecimal(1), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("finely grated orange zest", new BigDecimal(1), tableSpoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("fresh-squeezed orange juice", new BigDecimal(3),tableSpoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("olive oil", new BigDecimal(2), tableSpoonUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("boneless chicken thigh", new BigDecimal(4), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("small corn tortillas", new BigDecimal(8), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("packed baby arugula", new BigDecimal(3), cupUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("medium ripe avocados", new BigDecimal(2), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("thinly sliced radishes", new BigDecimal(4), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("cherry tomatoes", new BigDecimal(".5"), pintUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("red onion, thinly sliced", new BigDecimal(".25"),eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("chopped cilantro", new BigDecimal(4), eachUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("1/2 cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUom, chickenTacosRecipe ));
        chickenTacosRecipe.getIngredients().add( new Ingredient("lime cut into wedges", new BigDecimal(1),eachUom, chickenTacosRecipe ));
//        chickenTacosRecipe.getIngredients().add( new Ingredient("", new BigDecimal() ));

        chickenTacosRecipe.getCategories().add(mexicanCategory);

        chickenTacosRecipe.setServings(2);
        chickenTacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        chickenTacosRecipe.setSource("Simply Recipes");

        recipes.add(chickenTacosRecipe);

        return recipes;
    }
}

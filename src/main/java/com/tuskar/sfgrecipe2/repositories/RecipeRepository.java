package com.tuskar.sfgrecipe2.repositories;

import com.tuskar.sfgrecipe2.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,String> {
}

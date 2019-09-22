package com.tuskar.sfgrecipe2.commands;

import com.tuskar.sfgrecipe2.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 04-Sep-19
 */
@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {

    private String id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;
//    private Set<IngredientCommand> ingredients = new HashSet<>();
//    Spring MVC doesn't bind to a set, only to a list.
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
//    private Set<CategoryCommand> categories = new HashSet<>();
//    Spring MVC doesn't bind to a set, only to a list.
    private List<CategoryCommand> categories = new ArrayList<>();
}

package com.tuskar.sfgrecipe2.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 04-Sep-19
 */
@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId; // we need this for hidden variable in forms and web interaction.
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

}

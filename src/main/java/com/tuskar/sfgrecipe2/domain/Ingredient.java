package com.tuskar.sfgrecipe2.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Ingredient {

//    @Id
//    private String id;
//    Without this id was setting null, because @Id was just a list.
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;

    @DBRef
    private UnitOfMeasure uom;
//    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        //this.recipe = recipe;
    }

}

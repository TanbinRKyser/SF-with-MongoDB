package com.tuskar.sfgrecipe2.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Getter
@Setter
public class Notes {

    @Id
    private String id;
//    private Recipe recipe;
    private String recipeNotes;

}

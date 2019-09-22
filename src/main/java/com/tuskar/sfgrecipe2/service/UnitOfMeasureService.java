package com.tuskar.sfgrecipe2.service;

import com.tuskar.sfgrecipe2.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 06-Sep-19
 */
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}

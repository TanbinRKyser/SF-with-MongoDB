package com.tuskar.sfgrecipe2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Sfg-Recipe
 * <p>
 * Created by User on 09-Sep-19
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(){
        super();
    }

    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

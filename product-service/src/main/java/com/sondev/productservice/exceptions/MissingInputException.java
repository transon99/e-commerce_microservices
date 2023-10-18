package com.sondev.productservice.exceptions;

public class MissingInputException extends RuntimeException{
    public MissingInputException(String message){
        super(message);
    }
}

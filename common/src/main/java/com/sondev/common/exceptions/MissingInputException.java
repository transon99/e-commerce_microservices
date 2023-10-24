package com.sondev.common.exceptions;

public class MissingInputException extends RuntimeException{
    public MissingInputException(String message){
        super(message);
    }
}

package com.eastgate.serviceregisty.configserver.productservice.exceptions;

public class MissingInputException extends RuntimeException{
    public MissingInputException(String message){
        super(message);
    }
}

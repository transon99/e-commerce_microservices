package com.eastgate.productservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class APIException extends RuntimeException{
    public APIException(String message){
        super(message);
    }
}

package com.sondev.authservice.exceptions;

import com.sondev.common.exceptions.NotFoundException;
import com.sondev.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorResponse handleAuthenticationException(Exception ex) {
        return new ErrorResponse( HttpStatus.UNAUTHORIZED, "username or password is incorrect.", new Date());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerUserAlreadyExistException(UserAlreadyExistException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),new Date());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorResponse handleAccountStatusException(AccountStatusException ex) {
        return new ErrorResponse( HttpStatus.UNAUTHORIZED, "User account is abnormal.", new Date());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorResponse handleAccessDeniedException(AccessDeniedException ex) {
        return new ErrorResponse( HttpStatus.FORBIDDEN, "No permission.", new Date());
    }

//    @ExceptionHandler(InvalidBearerTokenException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    Result handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
//        return new Result(false, StatusCode.UNAUTHORIZED, "The access token provided is expired, revoked, malformed, or invalid for other reasons.", ex.getMessage());
//    }

}
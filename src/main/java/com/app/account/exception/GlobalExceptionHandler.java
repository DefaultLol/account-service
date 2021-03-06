package com.app.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    //custom errors
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    //custom errors
    @ExceptionHandler(AccountAmountNotEnoughException.class)
    public ResponseEntity<?> amountNotEnough(AccountAmountNotEnoughException exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //global exception handling
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

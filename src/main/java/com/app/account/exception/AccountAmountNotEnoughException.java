package com.app.account.exception;

public class AccountAmountNotEnoughException extends RuntimeException {
    public AccountAmountNotEnoughException(String message){
        super(message);
    }
}

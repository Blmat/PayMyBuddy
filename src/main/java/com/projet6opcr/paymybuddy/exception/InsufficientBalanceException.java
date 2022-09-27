package com.projet6opcr.paymybuddy.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException (String message) {
        super(message);
    }
}

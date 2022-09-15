package com.projet6opcr.paymybuddy.exception;

public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}

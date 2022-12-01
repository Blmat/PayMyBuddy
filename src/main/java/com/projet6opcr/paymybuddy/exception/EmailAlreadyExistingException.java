package com.projet6opcr.paymybuddy.exception;

public class EmailAlreadyExistingException extends RuntimeException {
    public EmailAlreadyExistingException(String message) {
        super(message);
    }
}

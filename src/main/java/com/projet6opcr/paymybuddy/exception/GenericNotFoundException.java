package com.projet6opcr.paymybuddy.exception;

public class GenericNotFoundException extends RuntimeException {

    public GenericNotFoundException() {
        super("Ressource not found");
    }

    public GenericNotFoundException(Class<?> entity, Integer id) {
        super("Ressource " + entity.getName() + " not found with id = " + id);
    }

    public GenericNotFoundException(String message) {
        super(message);
    }

    public GenericNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericNotFoundException(Throwable cause) {
        super(cause);
    }
}

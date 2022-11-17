package com.projet6opcr.paymybuddy.exception;

import com.projet6opcr.paymybuddy.model.UserAccount;

public class UserNotFoundException extends GenericNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Integer id) {
        super(UserAccount.class, id);
    }
}

package com.projet6opcr.paymybuddy.model.dto;

import com.projet6opcr.paymybuddy.model.UserAccount;
import lombok.Value;

import java.io.Serializable;

@Value
public class BuddyDto implements Serializable {

    private final String firstName;
    private final String lastName;
    private final String email;

    public BuddyDto(UserAccount userAccount) {
        firstName = userAccount.getFirstName();
        lastName = userAccount.getLastName();
        email = userAccount.getEmail();
    }

}

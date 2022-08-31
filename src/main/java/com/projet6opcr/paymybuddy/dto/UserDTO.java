package com.projet6opcr.paymybuddy.dto;

import lombok.Data;

@Data
public class UserDTO {


    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }


}

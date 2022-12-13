package com.projet6opcr.paymybuddy.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull(message = "FirstName cannot be null.")
    @NotEmpty(message = "FirstName cannot be empty.")
    private String firstName;

    @NotNull(message = "lastName cannot be null.")
    @NotEmpty(message = "lastName cannot be empty.")
    private String lastName;

    @NotNull(message = "email cannot be null.")
    @NotEmpty(message = "email cannot be empty.")
    @Email
    private String email;

    @NotNull(message = "password cannot be null.")
    @NotEmpty(message = "password cannot be empty.")
    @Length(min = 5, max = 25, message = "password not valid")
    private String password;
}

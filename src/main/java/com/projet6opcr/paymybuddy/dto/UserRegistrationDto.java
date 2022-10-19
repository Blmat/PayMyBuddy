package com.projet6opcr.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

	@Email
	private String mail;

	@NotBlank(message = "Password must not be empty")
	private String password;

	@NotBlank(message = "Password confirmation must not be empty")
	private String passwordConfirmation;

	@NotEmpty(message = "First name field must not be empty")
	private String firstName;

	@NotEmpty(message = "Last name field must not be empty")
	private String lastName;

}

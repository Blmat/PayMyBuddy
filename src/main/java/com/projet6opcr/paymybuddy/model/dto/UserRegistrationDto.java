package com.projet6opcr.paymybuddy.model.dto;

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

	@NotBlank
	private String password;

	@NotBlank
	private String passwordConfirmation;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

}

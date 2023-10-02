package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String identityCode;
	private String username;
	private String password;
}
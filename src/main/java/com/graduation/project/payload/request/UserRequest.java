package com.graduation.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String identityCode;
	private String phone;
	private String address;
//	private String role;
}
